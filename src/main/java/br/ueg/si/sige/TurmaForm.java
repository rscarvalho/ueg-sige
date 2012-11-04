package br.ueg.si.sige;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;

public class TurmaForm extends HttpServlet {
	private static final long serialVersionUID = -8496615419250990103L;

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioGerencial");
        if(usuario==null){
            response.sendRedirect("login");
        }
        
        if(request.getParameter("action")==null){
            //lista
            showMainForm(request, response);
        }else{
            String action = request.getParameter("action");
            if(action.equals("incluir")){
                //inclusao
                incluirTurma(request, response);
            }else if(action.equals("editar")){
                //edicao
                editarTurma(request, response);
            }else if(action.equals("excluir")){
                //exclusao
                excluirTurma(request, response);
            }else{
                //pesquisa
                Map<String, Object> campos = new HashMap<String, Object>();
                campos.put("nome", request.getParameter("entidade"));
                ArrayList<Entidade> entidades = EntidadeDAO.buscaParametrizada(campos);
                
                Set<Turma> turmas = new HashSet<Turma>();
                if(entidades!=null){
                    for(Iterator<Entidade> iter = entidades.iterator();iter.hasNext();){
                        Entidade entidade = (Entidade)iter.next();
                        campos.clear();
                        campos.put("entidade",entidade);
                        Set<Turma> turmasTemp = new TurmaDAO().buscaParametrizada(campos);
                        try{
                            turmas.addAll(turmasTemp);
                        }catch(NullPointerException ex){
                            
                        }
                    }
                    if(turmas.size()==0){
                        turmas = new TurmaDAO().buscaParametrizada(new HashMap<String, Object>());
                    }
                }
                
                request.setAttribute("turmas",turmas);
                showMainList(request, response);
            }
        }
    }
    
    private void showMainForm(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        TurmaDAO dao = new TurmaDAO();
        Set<Turma> turmas = dao.buscaParametrizada(new HashMap<String, Object>());
        request.setAttribute("turmas",turmas );
        showMainList(request, response);
    }
    
    private void excluirTurma(final HttpServletRequest request, final HttpServletResponse response)
    throws IOException, ServletException {
        if(!checkPermissions(request, "excluir")){
            showError(request, response, null);
        }else{
            request.setAttribute("action","excluir");
            if(request.getParameter("confirma")==null){
                //mostra confirmacao
                Turma turma = new TurmaDAO().buscaPorCodigo(Integer.parseInt(request.getParameter("codigo")));
                request.setAttribute("turma",turma);
                showFormConfirmaDados(request, response);
            }else{
                String erro = null;
                Turma turma = fillTurma(request);
                if(new TurmaDAO().excluir(turma)){
                    erro = "Turma exclu&iacute;da com sucesso!";
                }else{
                    erro = "Turma n&atilde;o exclu&iacute;da.";
                }
                request.setAttribute("erro",erro);
                showMainForm(request, response);
            }
        }
    }
    
    private void editarTurma(final HttpServletRequest request, final HttpServletResponse response)
    throws IOException, ServletException {
        
        if(!checkPermissions(request, "editar")){
            showError(request, response, null);
        }else{
            request.setAttribute("action","editar");
            if(request.getMethod().equalsIgnoreCase("get")){
                //mostra o formulario
                TurmaDAO dao = new TurmaDAO();
                Turma turma = dao.buscaPorCodigo(Integer.parseInt(request.getParameter("codigo")));
                request.setAttribute("turma",turma);
                showFormTurmas(request, response);
            }else if(request.getParameter("confirma")==null){
                //confirmacao
                Turma turma = fillTurma(request);
                
                request.setAttribute("turma",turma);
                showFormConfirmaDados(request, response);
            }else{
                //salva alteracoes
                String erro = null;
                Turma turma = fillTurma(request);
                TurmaDAO dao = new TurmaDAO();
                if(dao.salvarAlteracoes(turma)){
                    erro = "Altera&ccedil;&otilde;es salvas com sucesso!";
                }else{
                    erro = "Altera&ccedil;&otilde;es n&atilde;o salvas.";
                }
                request.setAttribute("erro",erro);
                showMainForm(request, response);
            }
        }
    }
    
    private void incluirTurma(final HttpServletRequest request, final HttpServletResponse response)
    throws IOException, ServletException {
        if(!checkPermissions(request, "incluir")){
            showError(request, response, null);
        }else{
            request.setAttribute("action","incluir");
            if(request.getMethod().equalsIgnoreCase("get")){
                //mostra formulario
                showFormTurmas(request, response);
            }else if(request.getParameter("confirma")==null){
                //mostra confirmacao
                Turma turma = fillTurma(request);
                
                request.setAttribute("turma",turma);
                showFormConfirmaDados(request, response);
            }else{
                //inclui a turma
                Turma turma = fillTurma(request);
                String erro = null;
                TurmaDAO dao = new TurmaDAO();
                if(dao.incluir(turma)){
                    erro = "Turma inclu&iacute;da com sucesso!";
                }else{
                    erro = "Turma n&atilde; inclu&iacute;da.";
                }
                request.setAttribute("erro",erro);
                showMainForm(request, response);
            }
        }
    }
    
    private void showFormConfirmaDados(final HttpServletRequest request, final HttpServletResponse response)
    throws IOException, ServletException {
        request.getRequestDispatcher("confirma_dados_turma.jsp").forward(request, response);
    }
    
    private void showMainList(final HttpServletRequest request, final HttpServletResponse response)
    throws IOException, ServletException {
        request.getRequestDispatcher("turmas.jsp").forward(request, response);
    }
    
    private Turma fillTurma(final HttpServletRequest request) {
        Turma turma = new Turma();
        try{
            turma.setCodigo(Integer.parseInt(request.getParameter("codigo")));
        }catch(NumberFormatException ex){
            turma.setCodigo(0);
        }
        turma.setEntidade(EntidadeDAO.buscaPorCodigo(Integer.parseInt(request.getParameter("entidade"))));
        turma.setSerie(new SerieDAO().buscaPorCodigo(Integer.parseInt(request.getParameter("serie"))));
        turma.setAno(Integer.parseInt(request.getParameter("ano")));
        turma.setLiteral(request.getParameter("literal").trim());
        
        return turma;
    }
    
    private void showFormTurmas(final HttpServletRequest request, final HttpServletResponse response)
    throws IOException, ServletException {
        
        ArrayList<Entidade> entidades = EntidadeDAO.buscaParametrizada(new HashMap<String, Object>());
        Set<Serie> series = new SerieDAO().buscaParametrizada(new HashMap<String, Object>());
        request.setAttribute("entidades", entidades);
        request.setAttribute("series", series);
        request.getRequestDispatcher("form_turma.jsp").forward(request, response);
    }
    
    private boolean checkPermissions(HttpServletRequest request,
            String descricao) {
        HashMap<String, Object> campos = new HashMap<String, Object>();
        campos.put("modulo", "turmas");
        campos.put("descricao", descricao);
        Usuario usuario = (Usuario) request.getSession().getAttribute(
                "usuarioGerencial");
        Permissao permissao = (Permissao) PermissaoDAO.buscaParametrizada(campos).get(0);
        
        return usuario.isPermitted(permissao);
    }
    
    private void showError(HttpServletRequest request, HttpServletResponse response, String message)
    throws ServletException, IOException{
        request.setAttribute("erro", message);
        request.getRequestDispatcher("erro.jsp").forward(request,response);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
}
