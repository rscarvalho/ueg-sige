package br.ueg.si.sige;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class FechaAnoForm extends HttpServlet {
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioAcademico");
        if (usuario==null){
            response.sendRedirect("/login");
        }
        
        if ( ! this.checkPermissions(request,"efetivar")){
            this.showError(request, response, null);
        }else{ 
            this.fechaAno(request, response);
        }
    }

    private void fechaAno(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        
        if (request.getMethod().equalsIgnoreCase("get")){
            this.showFormConfirmaDados(request, response);
        }else{
            BoletimDAO dao = new BoletimDAO();
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioAcademico");
            Map campos = new HashMap();
            campos.put("anoLetivo", new Integer(usuario.getEntidade().getAnoLetivo()));
            campos.put("Entidade", usuario.getEntidade());
            Set boletins = dao.buscaParametrizada(campos); 
            
            for (Iterator iter = boletins.iterator();iter.hasNext(); ){
                Boletim boletim = (Boletim) iter.next();
                Map disciplinas = new HashMap();
                for (Iterator iter1 = boletim.getItensBoletim().iterator();iter.hasNext(); ){
                    ItemBoletim item = (ItemBoletim) iter.next();
                    if(disciplinas.get(item.getDisciplina())==null){
                        //cria o item no Map
                        float[] notaFalta = new float[2];
                        notaFalta[1] = item.getFaltas();
                        notaFalta[0] = item.getNota();
                        disciplinas.put(item.getDisciplina(),notaFalta);
                    }else{
                        //incrementa
                        float[] notaFalta = (float[]) disciplinas.get(item.getDisciplina());
                        notaFalta[0] += item.getNota();
                        notaFalta[1] += item.getFaltas();
                        disciplinas.put(item.getDisciplina(), notaFalta);
                    }
                }
                
                boolean aprovado = true;
                
                Set indices = disciplinas.keySet();
                for(Iterator iter1 = indices.iterator(); iter1.hasNext();){
                    Disciplina disciplina = (Disciplina) iter1.next();
                    float[] soma = (float[]) disciplinas.get(disciplina);
                    soma[0] = soma[0]/4;
                    if(soma[0]<5.0) aprovado = false;
                    ItemBoletim item = new ItemBoletim();
                    item.setNota(soma[0]);
                    item.setFaltas((int)soma[1]);
                    item.setBimesLetivo(5);
                    item.setDisciplina(disciplina);
                    boletim.getItensBoletim().add(item);
                }     
                dao.salvarAlteracoes(boletim);
                if(aprovado){
                    boletim.getMatricula().getAluno().setEscolaridade(
                            boletim.getMatricula().getAluno().getEscolaridade()+1);
                }
                new AlunoDAO().salvarAlteracoes(boletim.getMatricula().getAluno());
            }
            
            usuario.getEntidade().setAnoLetivo(usuario.getEntidade().getAnoLetivo()+1);
            request.getSession().setAttribute("usuarioAcademico",usuario);
            new EntidadeDAO().salvarAlteracoes(usuario.getEntidade());
            
            campos = new HashMap();
            campos.put("entidade",usuario.getEntidade());
            campos.put("ativo","true");
            ArrayList alunos = new AlunoDAO().buscaParametrizada(campos);
            
            int[] vagas = new int[9];
            for(Iterator iter = alunos.iterator(); iter.hasNext();){
                Aluno aluno = (Aluno) iter.next();
                
                vagas[ aluno.getEscolaridade() ]++;                
            }
            
            //inicializa vetor de letras de turmas
            char[] turmas = new char[9];
            for(int i = 1; i<turmas.length; i++){
                turmas[i] = 'a';
            }
            
            for(int i=1; i< vagas.length; i++){
                int qtdeTurmas = vagas[i]/40;
                if(vagas[i]%40!=0){
                    qtdeTurmas++;
                }
                
                for(int j = 0; j < qtdeTurmas; j++){
                    Turma turma = new Turma();
                    turma.setAno(usuario.getEntidade().getAnoLetivo());
                    turma.setEntidade(usuario.getEntidade());
                    Map c = new HashMap();
                    c.put("serie",Integer.toString(i));
                    Set series = new SerieDAO().buscaParametrizada(c);
                    Serie serie = (Serie) series.iterator().next();
                    turma.setSerie(serie);
                    turma.setLiteral(""+turmas[ serie.getNumero() ]);
                    turmas[ serie.getNumero() ]++;
                    
                    turma.setMatriculas(new HashSet());
                    
                    new TurmaDAO().incluir(turma);
                }
            }
            
            request.getRequestDispatcher("main.jsp").forward(request,response);
        }
        
    }
   
    
    private void showFormConfirmaDados(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        request.getRequestDispatcher("confirma_fechar_ano.jsp").forward(request, response);
        }
      

    public String getServletInfo() {
        return "Short description";
    }

    private boolean checkPermissions(HttpServletRequest request, String descricao) {
        HashMap campos = new HashMap();
        campos.put("modulo", "fechar ano");
        campos.put("descricao", descricao);
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioAcademico");
        Permissao permissao = (Permissao) PermissaoDAO.buscaParametrizada(campos).get(0);
        
        return usuario.isPermitted(permissao);
    }
    
    private void showError(HttpServletRequest request, HttpServletResponse response, String message)
    throws ServletException, IOException{
        request.setAttribute("erro", message);
        request.getRequestDispatcher("erro.jsp").forward(request,response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
     
}
