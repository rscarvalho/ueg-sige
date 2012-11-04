/*
 * TransferenciaForm.java
 *
 * Created on 15 de Novembro de 2005, 12:16
 */

package br.ueg.si.sige;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 *
 * @author adelaide
 * @version
 */
public class TransferenciaForm extends HttpServlet {
	private static final long serialVersionUID = 1718996449724299788L;

	/** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Usuario usuarioAcademico = (Usuario)request.getSession().getAttribute("usuarioAcademico");
        if(usuarioAcademico==null){
            response.sendRedirect("/login");
        }        
        
        if(request.getParameter("action")==null){
            //mostra lista de alunos
            showMainAluno(request, response, new HashMap<String, Object>());
        } else {
            //mostra formulario para efetuar transferencia
            if (request.getParameter("action").equals("transferencia")){
                showFormTransferencia(request, response);
                //realiza pesquisa
            } else if (request.getParameter("action").equals("busca")){
                Map<String, Object> campos = new HashMap<String, Object>();
                campos.put("nome", request.getParameter("nome"));
                showMainAluno(request, response, campos);
                                
            } else if(request.getParameter("action").equals("transferencia")){
                if (request.getMethod().equalsIgnoreCase("get")){
                    showFormTransferencia(request, response);
                } else {
                    Aluno aluno = AlunoDAO.buscaPorCodigo(Integer.parseInt(request.getParameter("aluno")));
                    Entidade entidade = EntidadeDAO.buscaPorCodigo(Integer.parseInt(request.getParameter("entidade")));
                    Map<String, Object> campos = new HashMap<String, Object>();
                    //busca matricula referebte ao ano letivo do aluno
                    campos.put("ano", new Integer(entidade.getAnoLetivo()));
                    campos.put("aluno", aluno);
                    Set<Matricula> matriculas = new MatriculaDAO().buscaParametrizada(campos);
                    campos.clear();
                    Matricula matricula = (Matricula) matriculas.iterator().next();
                    //busca boletim do aluno
                    campos.put("matricula", matricula);
                    Set<Boletim> boletins = new BoletimDAO().buscaParametrizada(campos);
                    Boletim boletim = (Boletim) boletins.iterator().next();
                    //realiza a matricula do aluno na entidade selecionada
                    aluno.setEntidade(entidade);
                    campos.clear();
                    //busca a serie da escola
                    campos.put("escolaridade",  new Integer(aluno.getEscolaridade()));
                    Set<Serie> series = new SerieDAO().buscaParametrizada(campos);
                    Serie serie = (Serie) series.iterator().next();
                    //busca turmas da serie na escola
                    campos.clear();
                    campos.put("serie", serie);
                    Set<Turma> turmas = new TurmaDAO().buscaParametrizada(campos);
                    Turma novaTurma = null;
                    //encontra turma com vagas
                    for(Iterator<Turma> iter = turmas.iterator();iter.hasNext();){
                        Turma turma = (Turma) iter.next();
                        Map<String, Object> campos2 = new HashMap<String, Object>();
                        campos2.put("turma", turma);
                        if(new TurmaDAO().buscaParametrizada(campos2).size() < 40){
                            novaTurma = turma;
                            break;
                        }
                    }
                    Matricula matriculaNova = new Matricula();
                    matriculaNova.setAluno(aluno);
                    matriculaNova.setTurma(novaTurma);
                    boletim.setMatricula(matriculaNova);
                    
                    try{
                        new MatriculaDAO().incluir(matriculaNova);
                        new BoletimDAO().salvarAlteracoes(boletim);
                        AlunoDAO.salvarAlteracoes(aluno);
                        //exibir tela de sucesso
                        String erro = null;
                        erro = "Transferência efetuada com sucesso";
                        request.setAttribute("erro",erro);
                        showMainAluno(request, response, campos);
                    }catch(Exception ex){
                        //exibir erro
                    }
                }
                
             
                //excluir a matricula do aluno da escola atual
                }   
            }
        } 
    private void showMainAluno(HttpServletRequest request,HttpServletResponse response, Map<String, Object> campos)
    throws IOException,ServletException{
       Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioAcademico");
       campos.put("entidade", usuario.getEntidade());
       campos.put("ativo", "true");
       campos.put("matriculado", "true");
       ArrayList<Aluno> alunos = AlunoDAO.buscaParametrizada(campos);
       request.setAttribute("alunos", alunos);
       request.getRequestDispatcher("transferencia.jsp").forward(request, response);
    }
    
    private void showFormTransferencia(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        Aluno aluno = AlunoDAO.buscaPorCodigo(Integer.parseInt(request.getParameter("codigo")));
        request.setAttribute("Aluno", aluno);
        ArrayList<Entidade> entidades = EntidadeDAO.buscaParametrizada(new HashMap<String, Object>());
        request.setAttribute("entidades", entidades);
        request.getRequestDispatcher("form_transferencia.jsp").forward(request, response);
    } 
} 