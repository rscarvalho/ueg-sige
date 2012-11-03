/*
 * DossieForm.java
 *
 * Created on 5 de Outubro de 2005, 12:55
 */

package br.ueg.si.sige;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 *
 * @author bassboy
 * @version
 */
public class DossieForm extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioAcademico");
        if(usuario==null){
            response.sendRedirect("/login");
        }
        String action = request.getParameter("action");
        int a = 10;
        if(request.getSession().getAttribute("historico")==null){
            if(action==null){
                if(request.getParameter("codigo")==null){
                    
                    Map campos = new HashMap();
                    if(request.getParameter("search")!=null){
                        //coloca nome na busca
                        campos.put("nome",(String)request.getParameter("search"));
                    }
                    showMainList(request, response, usuario, campos);
                }
                
            }else if(action.equals("boletim")){
                //processa boletim por turma, ver sub-itens
            }else if(action.equals("detalhe")){
                initSession(request, response);
            }
        }else{
            if(action==null){
                //mostra detalhe do aluno
                showFormDetalhe(request, response, usuario);
            }else if(action.equals("salvar")){
                //salva alteracoes no historico
                salvarHistorico(request, response); 
            }else if(action.equals("sair")){
                //retira da sessao
                finalizeSession(request, response);
            }else if(action.equals("penalidade")){
                //processa penalidades (ver sub-itens)
                String sub = request.getParameter("sub");
                
                if(sub.equals("incluir")){
                    
                    incluirPenalidade(request, response);
                    
                }else if(sub.equals("cancelar")){
                    
                    if(request.getMethod().equalsIgnoreCase("get")){
                        //mostra o formulario
                        HistoricoEscolar historico = (HistoricoEscolar)request.
                                getSession().getAttribute("historico");
                        Penalidade penalidade = historico.buscaPenalidadePorCodigo
                                (Integer.parseInt(request.getParameter("codigo")));
                        
                        request.setAttribute("penalidade", penalidade);
                        request.getRequestDispatcher("confirma_dados_penalidade.jsp").
                                forward(request,response);
                    }else{
                        //cancela a penalidade
                        HistoricoEscolar historico = (HistoricoEscolar)request.
                                getSession().getAttribute("historico");
                        Penalidade penalidade = historico.buscaPenalidadePorCodigo
                                (Integer.parseInt(request.getParameter("penalidade")));
                        
                        historico.removerPenalidade(penalidade);
                        historico.getExcluidos().add(penalidade);
                        request.getSession().setAttribute("historico", historico);
                        response.sendRedirect("dossie");
                    }
                    
                }
                
            }else if(action.equals("fichaIndividual")){
                //abre atualizacao de ficha individual
            }else if(action.equals("historico")){
                //impressao de historico
            }else if(action.equals("mudaAno")){
                //processa mudanca de ano
                mudarAno(request, response);
            }else if(action.equals("boletim")){
                //impressao de boletim
            }
        }
    }

    private void incluirPenalidade(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {

        if(request.getMethod().equalsIgnoreCase("get")){
            //mostra formulario
            request.getRequestDispatcher("form_penalidade.jsp").
                    forward(request,response);
        }else{
            //inclui a penalidade
            String descricao = request.getParameter("descricao");
            String tipo = request.getParameter("tipo");
            Matricula matricula = ((Boletim)request.getSession().
                    getAttribute("boletim")).getMatricula();
            Penalidade penalidade = new Penalidade();
            penalidade.setDescricao(descricao);
            penalidade.setTipo(tipo);
            penalidade.setMatricula(matricula);
            HistoricoEscolar historico = (HistoricoEscolar)request.
                    getSession().getAttribute("historico");
            penalidade.setCodigo(historico.sequenciaPenalidade--);
            historico.inserirPenalidade(penalidade);
            request.getSession().setAttribute("historico", historico);
            response.sendRedirect("dossie");
        }
    }

    private void salvarHistorico(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {

        HistoricoEscolar historico = atualizaSessao(request);
        new HistoricoDAO().atualizar(historico);
        response.sendRedirect("dossie");
    }

    private void mudarAno(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {

        int ano = Integer.parseInt(request.getParameter("ano"));
        HistoricoEscolar historico = atualizaSessao(request);
        Boletim boletim = buscaBoletimPorAno(ano, historico);
        request.getSession().setAttribute("boletim", boletim);
        response.sendRedirect("dossie");
    }

    private Boletim buscaBoletimPorAno(final int ano, final HistoricoEscolar historico) {
        for(Iterator iter=historico.getBoletins().iterator();iter.hasNext();){
            Boletim boletim = (Boletim) iter.next();
            if(boletim.getAnoLetivo()==ano){
                return boletim;
            }
        }
        return null;
    }
    
    private HistoricoEscolar atualizaSessao(final HttpServletRequest request) {
        Boletim boletim = (Boletim) request.getSession().getAttribute("boletim");
        HistoricoEscolar historico = (HistoricoEscolar)
        request.getSession().getAttribute("historico");
        historico.atualizarBoletim(boletim);
        request.getSession().setAttribute("historico", historico);
        
        return historico;
    }
    
    private void finalizeSession(final HttpServletRequest request, final HttpServletResponse response)
    throws IOException {
        
        request.getSession().setAttribute("historico",null);
        request.getSession().setAttribute("boletim",null);
        response.sendRedirect("dossie");
    }
    
    private void initSession(final HttpServletRequest request, final HttpServletResponse response)
    throws IOException {
        
        HistoricoEscolar historico = new HistoricoDAO().
                buscaPorAluno(new AlunoDAO().buscaPorCodigo(
                Integer.parseInt(request.getParameter("codigo"))));
        request.getSession().setAttribute("historico", historico);
        response.sendRedirect("dossie");
    }
    
    private void showFormDetalhe(final HttpServletRequest request, final HttpServletResponse response, final Usuario usuario)
    throws IOException, ServletException {
        
        if(request.getSession().getAttribute("boletim")==null){
            HistoricoEscolar historico = (HistoricoEscolar) request.getSession().getAttribute("historico");
            Boletim boletimSessao = buscaBoletimPorAno(usuario.getEntidade().getAnoLetivo(), historico);
            request.getSession().setAttribute("boletim",boletimSessao);
            int a = 0;
        }
        
        request.getRequestDispatcher("dossie_detalhe.jsp").forward(request, response);
    }
    
    private void showMainList(final HttpServletRequest request, final HttpServletResponse response, final Usuario usuario, final Map campos)
    throws IOException, ServletException {
        
        MatriculaDAO dao = new MatriculaDAO();
        campos.put("entidade",usuario.getEntidade());
        campos.put("anoLetivo",new Integer(usuario.getEntidade().getAnoLetivo()));
        Set matriculas = dao.buscaParametrizada(campos);
        request.setAttribute("matriculas", matriculas);
        request.getRequestDispatcher("dossie_geral.jsp").forward(request, response);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    // </editor-fold>
}
