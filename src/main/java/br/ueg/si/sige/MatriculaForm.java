/*
 * MatriculaForm.java
 *
 * Created on 10 de Novembro de 2005, 22:10
 */

package br.ueg.si.sige;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 *
 * @author ueg
 * @version
 */
public class MatriculaForm extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Usuario usuarioAcademico = (Usuario)request.getSession().getAttribute("usuarioAcademico");
        if(usuarioAcademico==null){
            response.sendRedirect("/login");
        }else if(!checkPermissions(request, "efetivar")){
            showError(request, response, null);
        }else{
            
            if(request.getParameter("action")==null){
                HashMap campos = new HashMap();
                if(request.getParameter("search")==null){
                    //mostrar lista
                    this.showMainList(request,response, campos);
                }else{
                    //realiza pesquisa
                    campos.put("nome", request.getParameter("search"));
                    this.showMainList(request, response, campos);
                }
            }else{
                String action = request.getParameter("action");
                
                if(action.equals("matricular")){
                    //processa matricula
                    if(request.getParameter("exception")==null){
                        if(request.getParameter("serie")==null){
                            //mostra formulario para escolha da serie
                            showFormMatricula(request, response, usuarioAcademico);
                        }else{
                            //efetua as matriculas
                            Serie serie = new SerieDAO().buscaPorCodigo(
                                    Integer.parseInt(request.getParameter("serie")));
                            efetuaMatriculas(request, response, usuarioAcademico, serie);
                        }
                    }else{
                        //matricula os alunos
                        int i = Integer.parseInt(request.getParameter("total"));
                        ArrayList alunos = new ArrayList();
                        for(int j=0; j<i; i++){
                            if(request.getParameter("aluno["+j+"]")!=null){
                                alunos.add(new AlunoDAO().buscaPorCodigo(
                                        Integer.parseInt(request.getParameter(
                                        "aluno["+j+"]"))));
                            }
                        }
                        Set turmas = new HashSet();
                        /**
                         * @TODO testar (tomara q funcione)
                         */
                        showFormResumoMatricula(request, response, turmas);
                    }
                }else if(action.equals("rematricular")){
                    //processa rematricula
                }
            }
        }
    }
    
    private void efetuaMatriculas(HttpServletRequest request, HttpServletResponse response, Usuario usuarioAcademico,Serie serie)
    throws IOException, ServletException{
        Map campos = new HashMap();
        
        campos.put("entidade",usuarioAcademico.getEntidade());
        campos.put("anoLetivo", new Integer(usuarioAcademico.getEntidade().getAnoLetivo()));
        campos.put("serie", serie);
        
        Set turmas = new TurmaDAO().buscaParametrizada(campos);
        campos.remove("serie");
        campos.remove("anoLetivo");
        try{
            campos.put("matriculados","false");
            campos.put("escolaridade", ""+serie.getNumero());
            ArrayList alunos = new AlunoDAO().buscaParametrizada(campos);
            Iterator iterAlunos = alunos.iterator();
            
            for(Iterator iter = turmas.iterator();iter.hasNext();){
                Turma turma = (Turma) iter.next();
                int i = 0;
                MatriculaDAO dao = new MatriculaDAO();
                int vagas = 0;
                while(i<40 && iter.hasNext()){
                    Matricula matricula = new Matricula();
                    matricula.setAluno((Aluno)iterAlunos.next());
                    matricula.setTurma(turma);
                    dao.incluir(matricula);
                    vagas++;
                    iterAlunos.remove();
                }
                turma.getSerie().setVagas(vagas);
            }
            if(alunos.size()>0){
                showFormExcecao(request, response, alunos);
            }else{
                showFormResumoMatricula(request, response, turmas);
            }
        }catch(Exception ex){
            request.setAttribute("erro","Problemas na efetivacao das matriculas");
            showMainList(request, response, new HashMap());
        }
    }
    
    private void showFormResumoMatricula(HttpServletRequest request, HttpServletResponse response,Set turmas)
    throws IOException, ServletException{
        //mostra formulario de resumo
        request.setAttribute("turmas", turmas);
        request.getRequestDispatcher("resumo_matricula.jsp").forward(request, response);
    }
    
    private void showFormExcecao(HttpServletRequest request, HttpServletResponse response, ArrayList alunos)
    throws ServletException,IOException{
        //mostra formulario de excecao
        request.setAttribute("alunos", alunos);
        request.getRequestDispatcher("form_alunos_excecao.jsp").forward(request, response);
    }
    
    private void showFormMatricula(HttpServletRequest request, HttpServletResponse response, Usuario usuarioAcademico)
    throws IOException, ServletException{
        if(!checkPermissions(request, "efetivar")){
            showError(request, response, null);
        }else{
            
            Entidade entidade = usuarioAcademico.getEntidade();
            Map campos = new HashMap();
            campos.put("entidade", entidade);
            campos.put("ano",new Integer(entidade.getAnoLetivo()));
            
            //Calculo das vagas por serie
            Set turmas = new TurmaDAO().buscaParametrizada(campos);
            Map series = new HashMap();
            if(turmas==null) turmas = new HashSet();
            for(Iterator iter = turmas.iterator(); iter.hasNext();){
                Turma turma = (Turma) iter.next();
                Serie serie = turma.getSerie();
                serie.setVagas(0);
                series.put(new Integer(serie.getNumero()),serie);
            }
            
            for(Iterator iter = turmas.iterator(); iter.hasNext();){
                Turma turma = (Turma) iter.next();
                HashMap params = new HashMap();
                params.put("turma", turma);
                Set matriculas = new MatriculaDAO().buscaParametrizada(params);
                
                Serie serie = (Serie)series.get(new Integer(turma.getSerie().getNumero()));
                int vagas = (40-matriculas.size())+serie.getVagas();
                serie.setVagas(vagas);
                series.put(new Integer(serie.getNumero()), serie);
            }
            //fim do calculo
            
            //conversao Map <=> Set
            Set series2 = new HashSet();
            Set keys = series.keySet();
            for(Iterator iter = keys.iterator(); iter.hasNext();){
                Integer item = (Integer)iter.next();
                series2.add(series.get(item));
            }
            //fim
            
            request.setAttribute("series", series2);
            request.getRequestDispatcher("form_matricula.jsp").
                    forward(request, response);
        }
    }
    
    private void showMainList(HttpServletRequest request,HttpServletResponse response, Map campos)
    throws IOException,ServletException{
        Entidade entidade = ((Usuario)request.getSession().getAttribute("usuarioAcademico")).getEntidade();
        campos.put("entidade",entidade);
        campos.put("anoLetivo",new Integer(entidade.getAnoLetivo()-1));
        
        Set matriculas = new MatriculaDAO().buscaParametrizada(campos);
        Set matriculasAtuais = new HashSet();
        
        /*
         * retira as matriculas de alunos ja matriculados no ano letivo corrente da escola
         */
        for(Iterator iter = matriculas.iterator();iter.hasNext();){
            Matricula matricula = (Matricula) iter.next();
            campos.clear();
            campos.put("aluno", matricula.getAluno());
            campos.put("anoLetivo", new Integer(entidade.getAnoLetivo()));
            Set mats = new MatriculaDAO().buscaParametrizada(campos);
            if(mats.size()>0){
                matriculasAtuais.addAll(mats);
                iter.remove();
            }
        }
        
        request.setAttribute("matriculas",matriculas);
        request.setAttribute("matriculasAtuais",matriculasAtuais);
        
        request.getRequestDispatcher("matriculas.jsp").forward(request,response);
    }
    
    private void showError(HttpServletRequest request, HttpServletResponse response, String message)
    throws ServletException, IOException{
        request.setAttribute("erro", message);
        request.getRequestDispatcher("erro.jsp").forward(request,response);
    }
    
    private boolean checkPermissions(HttpServletRequest request,
            String descricao) {
        HashMap campos = new HashMap();
        campos.put("modulo", "matriculas");
        campos.put("descricao", descricao);
        Usuario usuario = (Usuario) request.getSession().getAttribute(
                "usuarioAcademico");
        Permissao permissao = (Permissao) PermissaoDAO.buscaParametrizada(campos).get(0);
        
        return usuario.isPermitted(permissao);
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
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
