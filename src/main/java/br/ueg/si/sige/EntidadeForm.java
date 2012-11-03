package br.ueg.si.sige;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EntidadeForm extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        Usuario usuario = (Usuario) request.getSession().getAttribute(
                "usuarioGerencial");
        if (usuario == null) {
            response.sendRedirect("login");
        }
        if (request.getParameter("action") == null) {
            ArrayList entidades = EntidadeDAO.buscarTodos();
            showFormListaEntidades(request, response, entidades);
        } else {
            String action = request.getParameter("action");
            if (action.equals("incluir")) {
                if (checkPermissions(request, "incluir")) {
                    //processo de inclusão de nova entidade
                    incluirEntidade(request, response);
                } else {
                    showError(request, response);
                }
            } else if (action.equals("editar")) {
                if (checkPermissions(request, "editar")) {
                    //processo de alteração de entidade
                    editarUsuario(request, response);
                } else {
                    showError(request, response);
                }
            } else if (action.equals("excluir")) {
                if (checkPermissions(request, "excluir")) {
                    //processo de exclusão de usuário
                    ExcluirEntidade(request, response);
                } else {
                    showError(request, response);
                }
            } else if (action.equals("pesquisar")) {
                //busca simples
                String pesquisa = request.getParameter("search");
                ArrayList entidades = EntidadeDAO.buscaPorNome(pesquisa);
                showFormListaEntidades(request, response, entidades);
            } else {
                //busca avançada
                if (request.getMethod().equalsIgnoreCase("get")) {
                    //exibe o formulario de pesquisa
                    request.getRequestDispatcher("busca_avancada_entidades.jsp").
                            forward(request, response);
                } else {
                    //processa a pesquisa
                    Map campos = new HashMap();
                    try {
                        if (!request.getParameter("nome").equals("")) {
                            campos.put("nome", request.getParameter("nome"));
                        }
                        if (!request.getParameter("endereco").equals("")) {
                            campos.put("endereco",
                                       request.getParameter("endereco"));
                        }
                        if (!request.getParameter("telefone").equals("")) {
                            campos.put("telefone",
                                       request.getParameter("telefone"));
                        }
                        if (!request.getParameter("qtde_salas").equals("")) {
                            if (request.getParameter("criterio").equals("ma")) {
                                campos.put("qtdeSalasMaior", new Integer(
                                        request.getParameter("qtde_salas")));
                            } else if (request.getParameter("criterio").equals(
                                    "me")) {
                                campos.put("qtdeSalasMenor", new Integer(
                                        request.getParameter("qtde_salas")));
                            } else {
                                campos.put("qtdeSalas", new Integer(
                                        request.getParameter("qtde_salas")));
                            }
                        }
                    } catch (Exception ex) {

                    }
                    ArrayList entidades = EntidadeDAO.buscaParametrizada(campos);
                    showFormListaEntidades(request, response, entidades);
                }
            }
        }
    }

    private void showError(HttpServletRequest request,
                           HttpServletResponse response) throws IOException,
            ServletException {
        request.getRequestDispatcher("erro.jsp").forward(request,
                response);
    }

    private void ExcluirEntidade(HttpServletRequest request,
                                 HttpServletResponse response) throws
            ServletException, IOException, NumberFormatException {
        String formAction = "excluir";
        if (request.getMethod().equalsIgnoreCase("get")) {
            Entidade entidade = EntidadeDAO.buscaPorCodigo(Integer.
                    parseInt(request.getParameter("codigo")));
            showFormConfirmaDados(request, response, formAction, entidade);
        } else {
            String erro = null;
            Entidade entidade = fillEntidade(request);
            if (EntidadeDAO.excluir(entidade)) {
                erro = "Entidade excluída";
            } else {
                erro = "Entidade não excluída";
            }
            request.setAttribute("erro", erro);
            ArrayList entidades = EntidadeDAO.buscarTodos();
            showFormListaEntidades(request, response, entidades);
        }
    }

    private void editarUsuario(HttpServletRequest request,
                               HttpServletResponse response) throws
            ServletException, IOException, NumberFormatException {
        String formAction = "editar";
        if (request.getMethod().equalsIgnoreCase("get")) {
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            Entidade entidade = EntidadeDAO.buscaPorCodigo(codigo);
            showFormEntidades(request, response, formAction, null, entidade);
        } else if (request.getParameter("confirma") == null) {
            Entidade entidade = fillEntidade(request);
            showFormConfirmaDados(request, response, formAction, entidade);
        } else {
            Entidade entidade = fillEntidade(request);
            String erro = "";
            if (EntidadeDAO.salvarAlteracoes(entidade)) {
                erro = "Alteracoes Salvas";
            } else {
                erro = "Entidade não alterada";
            }
            request.setAttribute("erro", erro);
            ArrayList entidades = EntidadeDAO.buscarTodos();
            showFormListaEntidades(request, response, entidades);
        }
    }

    private void incluirEntidade(HttpServletRequest request,
                                 HttpServletResponse response) throws
            ServletException, IOException {
        String formAction = "incluir";
        String erro = null;
        Entidade entidade = null;
        if (request.getMethod().equalsIgnoreCase("get")) {
            showFormEntidades(request, response, formAction, erro, null);
        } else if (request.getParameter("confirma") == null) {
            entidade = fillEntidade(request);
            showFormConfirmaDados(request, response, formAction,
                                  entidade);
        } else {
            entidade = fillEntidade(request);
            if (EntidadeDAO.incluir(entidade)) {
                erro = "Entidade incluida com sucesso!";
            } else {
                erro = "Entidade nao incluida";
            }
            request.setAttribute("erro", erro);
            ArrayList entidades = EntidadeDAO.buscarTodos();
            showFormListaEntidades(request, response, entidades);
        }
    }

    private Entidade fillEntidade(HttpServletRequest request) {
        Entidade entidade = new Entidade();
        entidade.setNome(request.getParameter("nome"));
        entidade.setEndereco(request.getParameter("endereco"));
        entidade.setTelefone(request.getParameter("telefone"));
        int qtdeSalas;
        try {
            qtdeSalas = Integer.parseInt(request.
                                         getParameter("qtde_salas"));
        } catch (NumberFormatException ex) {
            qtdeSalas = 0;
        }
        entidade.setSalasDeAula(qtdeSalas);
        int codigo;
        try {
            codigo = Integer.parseInt(request.getParameter(
                    "codigo"));
        } catch (NumberFormatException ex) {
            codigo = 0;
        }
        entidade.setCodigo(codigo);
        int anoLetivo;
        try{
            anoLetivo = Integer.parseInt(request.getParameter("ano_letivo"));
        }catch(NumberFormatException ex){
            anoLetivo = 0;
        }
        entidade.setAnoLetivo(anoLetivo);
        
        return entidade;
    }

    private void showFormConfirmaDados(HttpServletRequest request,
                                       HttpServletResponse response,
                                       String formAction, Entidade entidade) throws
            IOException, ServletException {
        request.setAttribute("entidade", entidade);
        request.setAttribute("formAction", formAction);
        request.getRequestDispatcher("confirma_dados_entidade.jsp").forward(
                request, response);
    }

    private void showFormEntidades(HttpServletRequest request,
                                   HttpServletResponse response,
                                   String formAction, String erro,
                                   Entidade entidade) throws IOException,
            ServletException {
        request.setAttribute("formAction", formAction);
        request.setAttribute("erro", erro);
        request.setAttribute("entidade", entidade);
        request.getRequestDispatcher("form_entidade.jsp").forward(request,
                response);
    }

    private void showFormListaEntidades(HttpServletRequest request,
                                        HttpServletResponse response,
                                        ArrayList entidades) throws IOException,
            ServletException {
        request.setAttribute("entidades", entidades);
        request.getRequestDispatcher("entidades.jsp").forward(request, response);
    }

    public boolean checkPermissions(HttpServletRequest request,
                                    String descricao) {
        HashMap campos = new HashMap();
        campos.put("modulo", "entidade");
        campos.put("descricao", descricao);
        Usuario usuario = (Usuario) request.getSession().getAttribute(
                "usuarioGerencial");
        Permissao permissao = (Permissao) PermissaoDAO.buscaParametrizada(
                campos).get(0);

        return usuario.isPermitted(permissao);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        doGet(req, resp);
    }
}
