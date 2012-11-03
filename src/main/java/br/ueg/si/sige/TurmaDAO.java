package br.ueg.si.sige;

import java.util.*;
import java.sql.*;

public class TurmaDAO {
    
    public boolean incluir(Turma turma) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("INSERT INTO turmas (ano,literal,cd_entidade,cd_serie) VALUES(?,?,?,?)");
            db.setInt(1, turma.getAno());
            db.setString(2, turma.getLiteral());
            db.setInt(3, turma.getEntidade().getCodigo());
            db.setInt(4, turma.getSerie().getCodigo());
            
            int i = db.executeUpdate();
            db.getPreparedStatement().close();
            
            db.createStatement();
            ResultSet rs = db.executeQuery("SELECT last_value FROM turmas_cd_turma_seq");
            rs.next();
            turma.setCodigo(rs.getInt("last_value"));
            rs.close();
            db.getStatement().close();
            db.prepareStatement("INSERT INTO sequencia_turma(cd_turma) values(?)");
            db.setInt(1, turma.getCodigo());
            db.executeUpdate();
            
            db.closeConnection();
            return i > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public boolean excluir(Turma turma) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("DELETE FROM sequencia_turmas WHERE cd_turma=?");
            db.setInt(1,turma.getCodigo());
            db.executeUpdate();
            db.getPreparedStatement().close();
            db.prepareStatement("DELETE FROM turmas WHERE cd_turma=?");
            db.setInt(1, turma.getCodigo());
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public boolean salvarAlteracoes(Turma turma) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("UPDATE turmas  SET ano=?,literal=?,cd_entidade=" +
                    "?,cd_serie=? WHERE cd_turma=?");
            db.setInt(1, turma.getAno());
            db.setString(2, turma.getLiteral());
            db.setInt(3, turma.getEntidade().getCodigo());
            db.setInt(4, turma.getSerie().getCodigo());
            db.setInt(5, turma.getCodigo());
            
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public Turma buscaPorCodigo(int codigo) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("SELECT * FROM turmas WHERE cd_turma=?");
            db.setInt(1, codigo);
            ResultSet rs = db.executeQuery();
            Turma turma = null;
            if(rs.next()){
                turma = new Turma();
                turma.setCodigo(rs.getInt("cd_turma"));
                turma.setAno(rs.getInt("ano"));
                turma.setLiteral(rs.getString("literal"));
                turma.setSerie(new SerieDAO().buscaPorCodigo(rs.getInt("cd_serie")));
                turma.setEntidade(new EntidadeDAO().buscaPorCodigo(rs.getInt("cd_entidade")));
                Map campos = new HashMap();
                campos.put("turma",turma);
                //turma.setMatriculas(new MatriculaDAO().buscaParametrizada(campos));
            }
            rs.close();
            db.closeConnection();
            return turma;
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
            StringBuffer sb = new StringBuffer("SELECT cd_turma FROM turmas WHERE true");
            
            if(campos.get("entidade")!=null){
                Entidade entidade = (Entidade) campos.get("entidade");
                sb.append(" AND cd_entidade="+entidade.getCodigo());
            }
            
            if(campos.get("anoLetivo")!=null){
                sb.append(" AND ano="+((Integer)campos.get("anoLetivo")).intValue());
            }
            
            if(campos.get("serie")!=null){
                Serie serie = (Serie) campos.get("serie");
                sb.append(" AND cd_serie="+serie.getCodigo());
            }
            
            String query = new String(sb);
            
            ResultSet rs = db.executeQuery(query);       
            Set resultado = null;
            
            if(rs.next()){
                resultado = new HashSet();
                do{
                    Turma turma = this.buscaPorCodigo(rs.getInt("cd_turma"));
                    resultado.add(turma);
                }while(rs.next());
            }
            rs.close();
            db.closeConnection();
            return resultado;
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return null;
    }
    
}

