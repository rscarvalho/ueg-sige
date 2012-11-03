package br.ueg.si.sige;

import java.util.*;
import java.sql.*;


public class PermissaoDAO {
    public static ArrayList buscaPorModulo(Modulo modulo) {
        ArrayList modulos = ModuloDAO.buscaPorPai(modulo);
        if (modulos != null) {
            SigeDataBase db = new SigeDataBase();
            ArrayList resultado = new ArrayList();
            try {
                db.prepareStatement(
                        "SELECT cd_permissao FROM permissoes WHERE cd_modulo=?");
                for (Iterator iter = modulos.iterator(); iter.hasNext(); ) {
                    //coleta todas as permissoes referentes a cada modulo
                    Modulo item = (Modulo) iter.next();
                    db.setInt(1, item.getCodigo());
                    ResultSet rs = db.executeQuery();
                    if (rs.next()) {
                        do {
                            Permissao permissao = buscaPorCodigo(rs.getInt(
                                    "cd_permissao"));
                            resultado.add(permissao);
                        } while (rs.next());
                    }
                    rs.close();
                }
                db.closeConnection();
                return resultado;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static Permissao buscaPorCodigo(int codigo) {
        SigeDataBase db = new SigeDataBase();
        String query = "SELECT * FROM permissoes WHERE cd_permissao=?";
        try {
            db.prepareStatement(query);
            db.setInt(1, codigo);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                Permissao resultado = new Permissao();
                resultado.setCodigo(rs.getInt("cd_permissao"));
                resultado.setDescricao(rs.getString("ds_permissao"));
                resultado.setModulo(ModuloDAO.buscaPorCodigo(rs.getInt(
                        "cd_modulo")));
                return resultado;
            }
            db.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            db.closeConnection();
        }
        return null;
    }

    public static Permissao[] buscaPorUsuario(Usuario usuario) {
        SigeDataBase db = new SigeDataBase();
        String query = "SELECT p.cd_permissao as codigo, p.ds_permissao as " +
                       "descricao, p.cd_modulo as cd_modulo FROM permissoes p, permissao_usuario pu where" +
                       " p.cd_permissao=pu.cd_permissao AND pu.cd_usuario=?";
        try {
            db.setPreparedStatement(db.getConn().prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY));
            db.setInt(1, usuario.getCodigo());
            ResultSet rs = db.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            rs.beforeFirst();
            Permissao[] resultado = new Permissao[count];
            int i = 0;
            while (rs.next()) {
                Permissao permissao = new Permissao();
                permissao.setCodigo(rs.getInt("codigo"));
                permissao.setDescricao(rs.getString("descricao"));
                permissao.setModulo(ModuloDAO.buscaPorCodigo(rs.getInt(
                        "cd_modulo")));
                resultado[i++] = permissao;
            }
            return resultado;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ArrayList buscarPermissoes() {
        SigeDataBase db = new SigeDataBase();
        try {
            db.createStatement();
            ResultSet rs = db.executeQuery("SELECT * FROM permissoes");
            if (rs.next()) {
                ArrayList resultado = new ArrayList();
                do {
                    Permissao permissao = new Permissao();
                    permissao.setCodigo(rs.getInt("cd_permissao"));
                    permissao.setDescricao(rs.getString("ds_permissao"));
                    Modulo modulo = (Modulo) ModuloDAO.buscaPorCodigo(rs.
                            getInt("cd_modulo"));
                    permissao.setModulo(modulo);
                    resultado.add(permissao);
                } while (rs.next());
                db.closeConnection();
                return resultado;
            }
            db.closeConnection();
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        db.closeConnection();
        return null;
    }

    public static ArrayList buscaParametrizada(Map campos) {
        SigeDataBase db = new SigeDataBase();
        String modulo = (String) campos.get("modulo");
        String descricao = (String) campos.get("descricao");
        Modulo moduloResultado = (Modulo) ModuloDAO.buscaPorNome(modulo).
                                 get(0);
        String query =
                "SELECT ds_permissao, cd_permissao FROM permissoes WHERE" +
                " lower(ds_permissao) LIKE lower(?) AND cd_modulo=?";
        try {
            db.prepareStatement(query);
            db.setString(1, '%' + descricao + '%');
            db.setInt(2, moduloResultado.getCodigo());
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                ArrayList resultado = new ArrayList();
                do {
                    Permissao permissao = PermissaoDAO.buscaPorCodigo(rs.getInt("cd_permissao"));
                    resultado.add(permissao);
                } while (rs.next());
                db.closeConnection();
                return resultado;
            }
            db.closeConnection();
        } catch (SQLException ex) {
            db.closeConnection();
            ex.printStackTrace();
        }
        return null;
    }
}
