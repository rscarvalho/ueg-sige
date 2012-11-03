package br.ueg.si.sige;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class SerieDAO {
    
    public boolean incluir(Serie serie) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("INSERT INTO series(numero) VALUES(?)");
            db.setInt(1, serie.getNumero());
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public boolean salvarAlteracoes(Serie serie) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("UPDATE series SET numero=? WHERE cd_serie=?");
            db.setInt(1, serie.getNumero());
            db.setInt(2, serie.getCodigo());
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public boolean excluir(Serie serie) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("DELETE FROM series WHERE cd_serie=?");
            db.setInt(1, serie.getCodigo());
            int i = db.executeUpdate();
            db.closeConnection();
            return i > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public Serie buscaPorCodigo(int codigo) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("SELECT * FROM series WHERE cd_serie=?");
            db.setInt(1, codigo);
            ResultSet rs = db.executeQuery();
            Serie serie = null;
            if(rs.next()){
                //encher o objeto
                serie = new Serie();
                serie.setCodigo(rs.getInt("cd_serie"));
                serie.setNumero(rs.getInt("numero"));
                
                Map campos = new HashMap();
                campos.put("serie",serie);
                
                serie.setDisciplinas(new DisciplinaDAO().buscaParametrizada(campos));
            }
            rs.close();
            db.closeConnection();
            return serie;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return null;
    }
    
    public Set buscaParametrizada(Map campos) {
        SigeDataBase db = new SigeDataBase();
        try{
            String query = "SELECT cd_serie FROM series WHERE true";
            
            if(campos.get("numero") != null){
                query += " AND numero="+((String)campos.get("numero"));
            }
            query += " ORDER BY numero";
            
            db.createStatement();
            
            ResultSet rs = db.executeQuery(query);
            Set resultado = null;
            if(rs.next()){
                resultado = new HashSet();
                do{
                    Serie serie = this.buscaPorCodigo(rs.getInt("cd_serie"));
                    resultado.add(serie);
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

