package br.ueg.si.sige;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatriculaDAO {
    
    public boolean incluir(Matricula matricula) {
        SigeDataBase db = new SigeDataBase();
        try{
            //prepara e incrementa o contador de sequencia da turma
            SigeDataBase db2 = new SigeDataBase();
            db2.setPreparedStatement(
                    db2.getConn().prepareStatement(
                    "SELECT sequencia,cd_turma FROM sequencia_turma"+
                    " WHERE cd_turma=?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
                    )
                    );
            db2.setInt(1, matricula.getTurma().getCodigo());
            ResultSet rs = db2.executeQuery();
            rs.next();
            int sequencia = rs.getInt("sequencia")+1;
            rs.updateInt("sequencia", sequencia);
            rs.updateRow();
            rs.close();
            db2.closeConnection();
            //fim
            
            String query = "INSERT INTO matriculas(numeracao,cd_turma,cd_aluno)"+
                    " VALUES(gera_matricula(?,?),?,?)";
            db.prepareStatement(query);
            db.setInt(1,matricula.getTurma().getCodigo());
            db.setInt(2, sequencia);
            db.setInt(3, matricula.getTurma().getCodigo());
            db.setInt(4, matricula.getAluno().getCodigo());
            int i = db.executeUpdate();
            db.closeConnection();
            
            //inclui um boletim vazio
            if(i>0){
                Boletim boletim = new Boletim();
                boletim.setAnoLetivo(matricula.getTurma().getAno());
                boletim.setEntidade(matricula.getTurma().getEntidade());
                boletim.setMatricula(matricula);
                boletim.setTurma(matricula.getTurma());
                boletim.setItensBoletim(new HashSet());
                new BoletimDAO().incluir(boletim);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public boolean excluir(Matricula matricula) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("DELETE FROM matriculas WHERE cd_matricula=?");
            db.setInt(1, matricula.getCodigo());
            int i = db.executeUpdate();
            db.closeConnection();
            
            return i>0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public boolean salvarAlteracoes(Matricula matricula) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("UPDATE matriculas SET cd_turma=?,cd_aluno=? WHERE cd_matricula=?");
            db.setInt(1, matricula.getTurma().getCodigo());
            db.setInt(2, matricula.getAluno().getCodigo());
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public Matricula buscaPorCodigo(int codigo) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("SELECT * FROM matriculas WHERE cd_matricula=?");
            db.setInt(1,codigo);
            ResultSet rs = db.executeQuery();
            if(rs.next()){
                Matricula matricula = new Matricula();
                
                matricula.setCodigo(rs.getInt("cd_matricula"));
                matricula.setNumeracao(rs.getString("numeracao"));
                matricula.setAluno(new AlunoDAO().buscaPorCodigo(rs.getInt("cd_aluno")));
                matricula.setTurma(new TurmaDAO().buscaPorCodigo(rs.getInt("cd_turma")));
                rs.close();
                db.closeConnection();
                
                return matricula;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return null;
    }
    
    public Set buscaParametrizada(Map campos) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.createStatement();
            StringBuffer sb = new StringBuffer("SELECT cd_matricula FROM matriculas WHERE true ");
            
            if(campos.get("aluno")!=null){
                sb.append(" AND cd_aluno="+((Aluno)campos.get("aluno")).getCodigo());
            }
            
            if(campos.get("turma")!=null){
                sb.append(" AND cd_turma="+((Turma)campos.get("turma")).getCodigo());
            }
            
            if(campos.get("numeracao")!=null){
                sb.append(" AND numeracao LIKE '%"+(String)campos.get("numeracao")+"%'");
            }
            
            if(campos.get("anoLetivo")!=null){
                sb.append(" AND cd_turma in(SELECT cd_turma from turmas where ano="+((Integer)campos.get("anoLetivo")).intValue()+")");
            }
            
            String query = new String(sb);
            
            ResultSet rs = db.executeQuery(query);
            
            Set resultado = new HashSet();
            if(rs.next()){
                do{
                    Matricula matricula = this.buscaPorCodigo(rs.getInt("cd_matricula"));
                    resultado.add(matricula);
                }while(rs.next());
            }
            db.closeConnection();
            return resultado;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return null;
    }
    
}

