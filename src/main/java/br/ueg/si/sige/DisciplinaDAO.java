package br.ueg.si.sige;

import java.sql.*;
import java.util.*;
import java.text.*;
import java.net.*;


public class DisciplinaDAO {
    
    
    public boolean incluir(Disciplina disciplina) {
        
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("INSERT INTO disciplinas (nome_disciplina, sigla_disciplina) VALUES(?,?)");
            db.setString(1, disciplina.getNome());
            db.setString(2, disciplina.getSigla());
            
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    
    public boolean salvarAlteracoes(Disciplina disciplina) {
        
        SigeDataBase db = new SigeDataBase();
        
        try{
            db.prepareStatement("UPDATE disciplinas SET nome_disciplina=?, " +
                    "sigla_disciplina=? WHERE cd_disciplina=?");
            db.setString(1, disciplina.getNome());
            db.setString(2, disciplina.getSigla());
            
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    
    public boolean excluir(Disciplina disciplina) {
        
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("DELETE * FROM disciplinas WHERE cd_disciplina=?");
            db.setInt(1, disciplina.getCodigo());
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    
    public Disciplina buscaPorCodigo(int codigo) {
        
        SigeDataBase db = new SigeDataBase();
        
        try{
            db.prepareStatement("SELECT * FROM disciplinas WHERE cd_disciplina=?");
            db.setInt(1, codigo);
            
            ResultSet rs = db.executeQuery();
            Disciplina disciplina = null;
            
            if(rs.next()){
                //enche o objeto
                disciplina = new Disciplina();
                disciplina.setCodigo(rs.getInt("cd_disciplina"));
                disciplina.setNome(rs.getString("nome_disciplina"));
                disciplina.setSigla(rs.getString("sigla_disciplina"));
            }
            rs.close();
            db.closeConnection();
            return disciplina;
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return null;
    }
    
    
    public Set buscaParametrizada(Map campos) {
        
        SigeDataBase db = new SigeDataBase();
        
        try{
            String query = "SELECT cd_disciplina FROM disciplinas WHERE true";
            
            if(campos.get("nome") != null){
                query += " AND nome_disciplina=" +((String)campos.get("nome"));
            }
            
            if(campos.get("serie") != null){
                query += " AND cd_disciplina in (SELECT cd_disciplina from " +
                        "disciplina_serie WHERE cd_serie="+((Serie)campos.
                        get("serie")).getCodigo()+")";
            }
            
            query += " ORDER BY nome_disciplina";
            db.createStatement();
            
            ResultSet rs = db.executeQuery(query);
            Set resultado = null;
            
            if(rs.next()){
                resultado = new HashSet();
                
                do{
                    Disciplina disciplina = this.buscaPorCodigo(rs.getInt("cd_disciplina"));
                    resultado.add(disciplina);
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

