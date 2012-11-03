package br.ueg.si.sige;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class LoginForm extends HttpServlet {
	private static final long serialVersionUID = 5291710812877995620L;

	/**
     * Sobrescreve doGet em HttpServlet
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @see javax.servlet.http.HttpServlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        if (request.getSession().getAttribute("usuarioGerencial") == null &&
            request.getSession().getAttribute("usuarioAcademico") == null) {
            doAuthentication(request, response);
        } else {
            if (request.getParameter("action") == null) {
                showFormModulo(request, response);
            } else {
                //processa acões como logout e mudança de senha
                String action = request.getParameter("action");
                if (action.equals("logout")) {
                    request.getSession().removeAttribute("usuarioGerencial");
                    request.getSession().removeAttribute("usuarioAcademico");
                    request.getSession().setAttribute("horario",null);
                    response.sendRedirect("login");
                } else if (action.equals("primeiro_login")) {
                    //primeiro login no sistema
                    doFirstLogin(request, response);
                } else if (action.equals("muda_senha")) {
                    //mudança de senha
                    if(request.getMethod().equalsIgnoreCase("get")){
                        //mostra formulario
                        request.getRequestDispatcher("muda_senha.jsp").forward(request, response);
                    }else{
                        //salva alterações e grava no banco
                        String session = "";
                        if(request.getSession().getAttribute("usuarioGerencial")!=null){
                            session = "usuarioGerencial";
                        }else{
                            session = "usuarioAcademico";
                        }
                        Usuario usuario = (Usuario) request.getSession().getAttribute(session);
                        if(usuario.getSenha().equals(request.getParameter("senhaAntiga"))){
                            salvaSenha(request, response);
                        }else{
                            showError(request, response, "Senhas não conferem");
                        }
                    }
                } else {
                    response.sendRedirect("index.jsp");
                }
            }
        }
    }

    private void doFirstLogin(HttpServletRequest request,
                              HttpServletResponse response) throws IOException,
            ServletException {
        if (request.getMethod().equalsIgnoreCase("get")) {
            //mostra formulario
            request.getRequestDispatcher("muda_primeira_senha.jsp").
                    forward(request, response);
        } else {
            salvaSenha(request, response);
        }
    }

    private void salvaSenha(HttpServletRequest request,
                            HttpServletResponse response) throws
            ServletException, IOException {
        //salva nova senha e registra o usuario novamente na sessao
        String senha = request.getParameter("senha");

        if (request.getSession().getAttribute(
                "usuarioGerencial") != null ||
            request.
            getSession().getAttribute("usuarioAcademico") != null) {
            String session = "";
            boolean isBoth = false;
            if (request.getSession().getAttribute(
                    "usuarioGerencial") != null) {
                session = "usuarioGerencial";
            } else {
                session = "usuarioAcademico";
            }

            if (request.getSession().getAttribute(
                    "usuarioGerencial") != null &&
                request.getSession().getAttribute(
                        "usuarioAcademico") != null) {
                isBoth = true;
            }

            Usuario usuario = (Usuario) request.getSession().
                              getAttribute(session);
            usuario.setSenha(senha);
            if (UsuarioDAO.salvarAlteracoes(usuario)) {
                if (isBoth) {
                    request.getSession().setAttribute(
                            "usuarioAcademico", usuario);
                    request.getSession().setAttribute(
                            "usuarioGerencial", usuario);
                } else {
                    request.getSession().setAttribute(session,
                            usuario);
                }
                response.sendRedirect("login");
            } else {
                String erro = "Senha não alterada";
                showError(request, response, erro);
            }

        }
    }

    private void showError(HttpServletRequest request,
                           HttpServletResponse response, String erro) throws
            IOException, ServletException {
        request.setAttribute("erro", erro);
        request.getRequestDispatcher("erro.jsp").forward(request, response);
    }

    private void showFormModulo(HttpServletRequest request,
                                HttpServletResponse response) throws
            ServletException, IOException {

        if (request.getSession().getAttribute("usuarioGerencial") != null &&
            request.getSession().getAttribute("usuarioAcademico") != null) {
            //permissao_2_modulos
            Usuario usuario = (Usuario) request.getSession().getAttribute(
                    "usuarioGerencial");
            if (usuario.getCPF().equals(usuario.getSenha())) {
                response.sendRedirect("login?action=primeiro_login");
            } else {
                request.getRequestDispatcher("permissao_2_modulos.htm").forward(
                        request, response);
            }
        } else if (request.getSession().getAttribute("usuarioGerencial") != null) {
            //permissao_gerencial
            Usuario usuario = (Usuario) request.getSession().getAttribute(
                    "usuarioGerencial");
            if (usuario.getCPF().equals(usuario.getSenha())) {
                response.sendRedirect("login?action=primeiro_login");
            } else {
                response.sendRedirect("index.jsp");
            }
        } else if (request.getSession().getAttribute("usuarioAcademico") != null) {
            //permissao_academico
            Usuario usuario = (Usuario) request.getSession().getAttribute(
                    "usuarioAcademico");
            if (usuario.getCPF().equals(usuario.getSenha())) {
                response.sendRedirect("login?action=primeiro_login");
            } else {
                response.sendRedirect("academico/index.jsp");
            }
        }
    }

    private void doAuthentication(HttpServletRequest request,
                                  HttpServletResponse response) throws
            IOException, ServletException {
        if (request.getParameter("login") == null) {
            request.getRequestDispatcher("login.jsp").forward(request,
                    response);
        } else {
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            Usuario usuario = UsuarioDAO.buscaPorLoginSenha(login, senha);
            if (usuario == null) {
                request.setAttribute("erro",
                                     "<b>Login<b> ou <b>Senha<b> incorretos");
                request.getRequestDispatcher("login.jsp").forward(request,
                        response);
            } else if (!usuario.isAtivo()) {
                request.setAttribute("erro",
                                     "Usuário temporariamente desativado");
                request.getRequestDispatcher("login.jsp").forward(request,
                        response);
            } else {
                Map<String, Object> campos = new HashMap<String, Object>();
                campos.put("modulo", "sigegerencial");

                //verifica se o usuario possui permissões no modulo gerencial
                Modulo moduloGerencial = (Modulo) ModuloDAO.buscaPorNome(
                        "sigegerencial").get(0);
                ArrayList<Permissao> permissaoGerencial = PermissaoDAO.buscaPorModulo(
                        moduloGerencial);
                for (Iterator<Permissao> iter = permissaoGerencial.iterator();
                                     iter.hasNext(); ) {
                    Permissao item = (Permissao) iter.next();
                    if (usuario.isPermitted(item)) {
                        request.getSession().setAttribute("usuarioGerencial",
                                usuario);
                        break;
                    }
                }

                campos.remove("modulo");
                campos.put("modulo", "sigeacademico");

                //verifica se o usuário possui permissões no módulo acadêmico
                Modulo moduloAcademico = (Modulo) ModuloDAO.buscaPorNome(
                        "academico").get(0);
                ArrayList<Permissao> permissaoAcademico = PermissaoDAO.buscaPorModulo(
                        moduloAcademico);
                for (Iterator<Permissao> iter = permissaoAcademico.iterator();
                                     iter.hasNext(); ) {
                    Permissao item = (Permissao) iter.next();
                    if (usuario.isPermitted(item)) {
                        request.getSession().setAttribute("usuarioAcademico",
                                usuario);
                        break;
                    }
                }
                request.getRequestDispatcher("login").forward(request,
                        response);
            }
        }
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
}
