package br.ueg.si.sige;

import java.sql.*;
import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ModuloDAO {
    public static ArrayList buscaPorPai(Modulo modulo){
        SigeDataBase db = new SigeDataBase();
        try {
            db.prepareStatement("SELECT cd_modulo FROM modulos WHERE cd_modulo_pai=?");
            db.setInt(1, modulo.getCodigo());
            ResultSet rs = db.executeQuery();
            if(rs.next()){
                ArrayList resultado = new ArrayList();
                do{
                   Modulo moduloTemp = buscaPorCodigo(rs.getInt("cd_modulo"));
                   resultado.add(moduloTemp);
                }while(rs.next());
                rs.close();
                db.closeConnection();
                return resultado;
            }
            rs.close();
            db.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static Modulo buscaPorCodigo(int codigo) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.prepareStatement("SELECT * from modulos where cd_modulo=?");
            db.setInt(1, codigo);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                Modulo modulo = new Modulo();
                modulo.setCodigo(rs.getInt("cd_modulo"));
                modulo.setDescricao(rs.getString("ds_modulo"));
                if (rs.getInt("cd_modulo") == rs.getInt("cd_modulo_pai")) {
                    modulo.setModuloPai(null);
                } else {
                    modulo.setModuloPai(ModuloDAO.buscaPorCodigo(rs.
                            getInt("cd_modulo_pai")));
                }
                rs.close();
                db.closeConnection();
                return modulo;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            db.closeConnection();
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList buscaPorNome(String nome) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.prepareStatement("SELECT * FROM modulos WHERE lower(ds_modulo) LIKE lower(?)");
            db.setString(1, '%' + nome + '%');
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                ArrayList resultado = new ArrayList();
                do {
                    Modulo modulo = new Modulo();
                    modulo.setCodigo(rs.getInt("cd_modulo"));
                    modulo.setDescricao(rs.getString("ds_modulo"));
                    if (rs.getInt("cd_modulo") == rs.getInt("cd_modulo_pai")) {
                    modulo.setModuloPai(null);
                } else {
                    modulo.setModuloPai(ModuloDAO.buscaPorCodigo(rs.
                            getInt("cd_modulo_pai")));
                }

                    resultado.add(modulo);
                } while (rs.next());
                rs.close();
                db.closeConnection();
                return resultado;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            db.closeConnection();
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean salvarAlteracoes(Modulo modulo) {
        SigeDataBase db = new SigeDataBase();
        try {
            String query = "UPDATE modulos SET cd_modulo=?, cd_modulo_pai=?" +
                           "ds_modulo=? WHERE cd_modulo=?";
            db.prepareStatement(query);
            db.setInt(1, modulo.getCodigo());
            db.setInt(2, modulo.getModuloPai().getCodigo());
            db.setString(3, modulo.getDescricao());
            db.setInt(4, modulo.getCodigo());
            int resultado = db.executeUpdate();
            db.closeConnection();
            return (resultado > 0);
        } catch (SQLException ex) {
            db.closeConnection();
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean incluir(Modulo modulo) {
        SigeDataBase db = new SigeDataBase();
        try {
            String query =
                    "INSERT INTO modulos(cd_modulo, cd_modulo_pai, ds_modulo)" +
                    " VALUES(default,?,?)";
            db.prepareStatement(query);
            db.setInt(1, modulo.getModuloPai().getCodigo());
            db.setString(2, modulo.getDescricao());
            int resultado = db.executeUpdate();
            db.closeConnection();
            return (resultado > 0);
        } catch (SQLException ex) {
            db.closeConnection();
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean excluir(Modulo modulo) {
        SigeDataBase db = new SigeDataBase();
        try {
            String query = "DELETE FROM modulos WHERE cd_modulo=?";
            db.prepareStatement(query);
            db.setInt(1, modulo.getCodigo());
            int resultado = db.executeUpdate();
            db.closeConnection();
            return (resultado > 0);
        } catch (SQLException ex) {
            db.closeConnection();
            ex.printStackTrace();
            return false;
        }
    }

}
