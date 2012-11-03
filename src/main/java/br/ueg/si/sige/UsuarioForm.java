package br.ueg.si.sige;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class UsuarioForm extends HttpServlet {

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String action = request.getParameter("action");
        String search = request.getParameter("search");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioGerencial");
        if(usuario==null){
            response.sendRedirect("login");
        }
        if (action == null) {
            ArrayList usuarios = null;
            String redirect = "usuarios.jsp";
            if (search == null) {
                usuarios = UsuarioDAO.buscarTodos();
            } else {
                if (!request.getParameter("search").equals("avancada")) {
                    String nome = request.getParameter(
                            "search");
                    usuarios = UsuarioDAO.buscaPorNome(nome);
                } else {
                    if (request.getMethod().equalsIgnoreCase("get")) {
                        redirect = "busca_avancada.jsp";
                        ArrayList permissoes = PermissaoDAO.buscarPermissoes();
                        ArrayList entidades = EntidadeDAO.buscarTodos();
                        request.setAttribute("entidades", entidades);
                        request.setAttribute("permissoes", permissoes);
                        request.setAttribute("totalPermissoes",
                                             Integer.toString(permissoes.size()));
                    } else {
                        //preenche os campos para eviá-los à pesquisa
                        String nomeCompleto = request.getParameter(
                                "nomeCompleto");
                        String login = request.getParameter("login");
                        Entidade entidade = EntidadeDAO.buscaPorCodigo(Integer.
                                parseInt(request.getParameter("entidade")));
                        String status = request.getParameter("status");
                        String cpf = request.getParameter("cpf");
                        int totalPermissoes = Integer.parseInt(request.
                                getParameter("totalPermissoes"));
                        ArrayList permissoes = new ArrayList();
                        for (int i = 0; i < totalPermissoes; i++) {
                            int codigoPermissao;
                            try {
                                codigoPermissao = Integer.parseInt(request.
                                        getParameter("permissao" + i));
                            } catch (NumberFormatException ex) {
                                codigoPermissao = 0;
                            }
                            Permissao permissao = null;
                            if (codigoPermissao != 0) {
                                permissao = PermissaoDAO.buscaPorCodigo(
                                        codigoPermissao);
                            }
                            if (permissao != null) {
                                permissoes.add(permissao);
                            }
                        }
                        if(permissoes.size()==0)
                            permissoes = null;
                        HashMap campos = new HashMap();
                        campos.put("nomeCompleto", nomeCompleto);
                        campos.put("login", login);
                        campos.put("cpf", cpf);
                        campos.put("status", status);
                        campos.put("entidade", entidade);
                        campos.put("permissoes", permissoes);
                        usuarios = UsuarioDAO.buscaParametrizada(campos);
                    }
                }
            }
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher(redirect).forward(request,
                    response);
        } else {
            if (action.equals("incluir")) {
                //Formulario de inclusão de usuários
                IncluirUsuario(request, response);
            } else if (action.equals("editar")) {
                editarUsuario(request, response);
            } else if (action.equals("excluir")) {
                exlcuirUsuario(request, response);
            }
        }
    }

    private void exlcuirUsuario(HttpServletRequest request,
                                HttpServletResponse response) throws
            NumberFormatException, ServletException, IOException {
        if (!checkPermissions(request, "excluir")) {
            showError(request, response);
        } else {
            Usuario novoUsuario = UsuarioDAO.buscaPorCodigo(Integer.
                    parseInt(request.getParameter("codigo")));
            if (request.getMethod().equalsIgnoreCase("get")) {
                showFormConfirmaDados(request, response, novoUsuario,
                                      "excluir");
            } else if (request.getParameter("confirma").equals("S")) {
                UsuarioDAO.excluir(novoUsuario);
                response.sendRedirect("usuarios");
            } else {
                response.sendRedirect("usuarios");
            }
        }
    }

    private void editarUsuario(HttpServletRequest request,
                               HttpServletResponse response) throws
            NumberFormatException, ServletException, IOException {
        if (!checkPermissions(request, "editar")) {
            showError(request, response);
        } else {
            if (request.getMethod().equalsIgnoreCase("get")) {
                showFormIncluirUsuario(request, response, null,
                                       UsuarioDAO.buscaPorCodigo(
                                               Integer.parseInt(request.
                        getParameter("codigo"))),
                                       "editar");
            } else {
                if (request.getParameter("confirma") == null) {
                    Usuario novoUsuario = fillUsuario(request);
                    ArrayList temp;
                    if ((temp = UsuarioDAO.buscaPorLogin(novoUsuario.
                            getLogin())) != null) {
                        Usuario usuarioTemp = (Usuario) temp.get(0);
                        if (usuarioTemp.getCodigo() !=
                            novoUsuario.getCodigo()) {
                            showFormIncluirUsuario(request, response,
                                    "Usuario encontrado: " +
                                    usuarioTemp.getNomeCompleto() +
                                    " Código " + usuarioTemp.getCodigo() +
                                    "!=" + novoUsuario.getNomeCompleto(),
                                    novoUsuario, "editar");
                        } else {
                            showFormConfirmaDados(request, response,
                                                  novoUsuario, "editar");
                        }
                    } else {
                        showFormConfirmaDados(request, response,
                                              novoUsuario, "editar");
                    }
                } else if (request.getParameter("confirma").equals("S")) {
                    Usuario novoUsuario = fillUsuario(request);
                    if (UsuarioDAO.salvarAlteracoes(novoUsuario)) {
                        response.sendRedirect("usuarios");
                    } else {
                        showFormIncluirUsuario(request, response,
                                               "Usuário não atualizado",
                                               novoUsuario,
                                               "editar");
                    }

                } else {

                }
            }
        }
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }

    public void showError(HttpServletRequest request,
                          HttpServletResponse response) throws IOException,
            ServletException {
        request.getRequestDispatcher("erro.jsp").forward(request, response);
    }

    public void IncluirUsuario(HttpServletRequest request,
                               HttpServletResponse response) throws
            IOException, ServletException {
        //checa se o usuario tem permissoes de acesso a este modulo
        if (!checkPermissions(request, "incluir")) {
            showError(request, response);
        } else {

            if (request.getMethod().equalsIgnoreCase("get")) {
                showFormIncluirUsuario(request, response, null, null, "incluir");
            } else {
                if (request.getParameter("confirma") == null) {
                    //Recebe os parâmetros da requisição da primeira tela (formulário de cadastro)
                    Usuario usuario = fillUsuario(request);
                    //verifica se não há nenhum usuário com o mesmo login
                    if ((UsuarioDAO.buscaPorLogin(usuario.getLogin())) != null) {
                        showFormIncluirUsuario(request, response,
                                               "Este login já existe. Por favor, escolha outro.",
                                               usuario, "incluir");
                    } else {
                        showFormConfirmaDados(request, response, usuario,
                                              "incluir");
                    }
                } else {
                    //Inclui o Usuário no banco de dados
                    if (request.getParameter("confirma").equals("S")) {
                        /**
                         * @todo Salvar o usuario
                         */
                        Usuario usuario = fillUsuario(request);
                        if (UsuarioDAO.incluir(usuario)) {
                            response.sendRedirect("usuarios");
                        } else {
                            showFormIncluirUsuario(request, response,
                                    "Usuário não incluído", usuario, "incluir");
                        }
                    } else {
                        Usuario usuario = fillUsuario(request);
                        showFormIncluirUsuario(request, response, null, usuario,
                                               "incluir");
                    }
                }
            }
        }
    }

    private void showFormConfirmaDados(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Usuario usuario, String acaoFormulario) throws
            IOException, ServletException {
        String redirectURL = "confirma_dados.jsp";
        request.setAttribute("usuario", usuario);
        request.setAttribute("acaoFormulario", acaoFormulario);
        request.getRequestDispatcher(redirectURL).forward(
                request,
                response);
    }

    /**
     * Preenche o usuário com dados da requisição
     * @param request HttpServletRequest
     * @return Usuario
     * @throws NumberFormatException
     */
    private Usuario fillUsuario(HttpServletRequest request) throws
            NumberFormatException {
        String senha = (request.getParameter("geraSenha").equals("true")) ?
                       "" :
                       null;
        int codigo;
        try {
            codigo = Integer.parseInt(request.getParameter("codigo"));
        } catch (NumberFormatException ex) {
            codigo = 0;
        }
        int totalPermissoes = Integer.parseInt(request.getParameter(
                "totalPermissoes"));
        ArrayList permissoesTemp = new ArrayList();
        for (int i = 0; i < totalPermissoes; i++) {
            String permissao = request.getParameter("permissao" + i);
            if (permissao != null) {
                permissoesTemp.add(new PermissaoDAO().buscaPorCodigo(
                        Integer.parseInt(permissao)));
            }
        }
        Permissao[] permissoes = new Permissao[permissoesTemp.size()];
        for (int i = 0; i < permissoesTemp.size(); i++) {
            permissoes[i] = (Permissao) permissoesTemp.get(i);
        }

        Usuario usuario = new Usuario();
        usuario.setCodigo(codigo);
        usuario.setNomeCompleto(request.getParameter("nomeCompleto"));
        usuario.setLogin(request.getParameter("login"));
        usuario.setCPF(request.getParameter("cpf"));
        usuario.setEntidade(new EntidadeDAO().buscaPorCodigo(Integer.
                parseInt(request.getParameter("entidade"))));
        usuario.setAtivo((request.getParameter("ativo").equals("true")));
        usuario.setSenha((senha!=null)?usuario.getCPF():senha);
        usuario.setPermissoes(permissoes);
        return usuario;
    }

    private void showFormIncluirUsuario(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String erro, Usuario novoUsuario,
                                        String action) throws
            ServletException, IOException {
        //exibe formulário de inclusão
        ArrayList permissoes = new PermissaoDAO().buscarPermissoes();
        ArrayList entidades = new EntidadeDAO().buscarTodos();
        request.setAttribute("entidades", entidades);
        request.setAttribute("permissoes", permissoes);
        request.setAttribute("usuario", novoUsuario);
        request.setAttribute("erro", erro);
        request.setAttribute("totalPermissoes",
                             Integer.toString(permissoes.size()));
        request.setAttribute("acaoFormulario", action);
        request.getRequestDispatcher("form_usuario.jsp").forward(
                request,
                response);
    }

    private boolean checkPermissions(HttpServletRequest request,
                                    String descricao) {
        HashMap campos = new HashMap();
        campos.put("modulo", "usuarios");
        campos.put("descricao", descricao);
        Usuario usuario = (Usuario) request.getSession().getAttribute(
                "usuarioGerencial");
        Permissao permissao = (Permissao) PermissaoDAO.buscaParametrizada(campos).get(0);

        return usuario.isPermitted(permissao);
    }
}
