/*
 * TransferenciaForm.java
 *
 * Created on 15 de Novembro de 2005, 12:16
 */

package br.ueg.si.sige;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 *
 * @author adelaide
 * @version
 */
public class TransferenciaForm extends HttpServlet {
    
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
            showMainAluno(request, response, new HashMap());
        } else {
            //mostra formulário para efetuar transferência
            if (request.getParameter("action").equals("transferencia")){
                showFormTransferencia(request, response);
                //realiza pesquisa
            } else if (request.getParameter("action").equals("busca")){
                Map campos = new HashMap();
                campos.put("nome", request.getParameter("nome"));
                showMainAluno(request, response, campos);
                                
            } else if(request.getParameter("action").equals("transferencia")){
                if (request.getMethod().equalsIgnoreCase("get")){
                    showFormTransferencia(request, response);
                } else {
                    Aluno aluno = new AlunoDAO().buscaPorCodigo(Integer.parseInt(request.getParameter("aluno")));
                    Entidade entidade = new EntidadeDAO().buscaPorCodigo(Integer.parseInt(request.getParameter("entidade")));
                    Map campos = new HashMap();
                    //busca matricula referebte ao ano letivo do aluno
                    campos.put("ano", new Integer(entidade.getAnoLetivo()));
                    campos.put("aluno", aluno);
                    Set matriculas = new MatriculaDAO().buscaParametrizada(campos);
                    campos.clear();
                    Matricula matricula = (Matricula) matriculas.iterator().next();
                    //busca boletim do aluno
                    campos.put("matricula", matricula);
                    Set boletins = new BoletimDAO().buscaParametrizada(campos);
                    Boletim boletim = (Boletim) boletins.iterator().next();
                    //realiza a matricula do aluno na entidade selecionada
                    aluno.setEntidade(entidade);
                    campos.clear();
                    //busca a serie da escola
                    campos.put("escolaridade",  new Integer(aluno.getEscolaridade()));
                    Set series = new SerieDAO().buscaParametrizada(campos);
                    Serie serie = (Serie) series.iterator().next();
                    //busca turmas da serie na escola
                    campos.clear();
                    campos.put("serie", serie);
                    Set turmas = new TurmaDAO().buscaParametrizada(campos);
                    Turma novaTurma = null;
                    //encontra turma com vagas
                    for(Iterator iter = turmas.iterator();iter.hasNext();){
                        Turma turma = (Turma) iter.next();
                        Map campos2 = new HashMap();
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
                        new AlunoDAO().salvarAlteracoes(aluno);
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
    private void showMainAluno(HttpServletRequest request,HttpServletResponse response, Map campos)
    throws IOException,ServletException{
       Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioAcademico");
       campos.put("entidade", usuario.getEntidade());
       campos.put("ativo", "true");
       campos.put("matriculado", "true");
       ArrayList alunos = new AlunoDAO().buscaParametrizada(campos);
       request.setAttribute("alunos", alunos);
       request.getRequestDispatcher("transferencia.jsp").forward(request, response);
    }
    
    private void showFormTransferencia(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        Aluno aluno = new AlunoDAO().buscaPorCodigo(Integer.parseInt(request.getParameter("codigo")));
        request.setAttribute("Aluno", aluno);
        ArrayList entidades = new EntidadeDAO().buscaParametrizada(new HashMap());
        request.setAttribute("entidades", entidades);
        request.getRequestDispatcher("form_transferencia.jsp").forward(request, response);
    } 
} 