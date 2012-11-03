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
public class EntidadeDAO {

    /**
     * Busca uma entidade com base no código, e retorna um Value Object com dados
     * da entidade
     * @param codigo int
     * @return Entidade
     */
    public static Entidade buscaPorCodigo(int codigo) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.prepareStatement("SELECT * FROM entidades WHERE cd_entidade=?");
            db.setInt(1, codigo);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                Entidade entidade = new Entidade();
                entidade.setCodigo(rs.getInt("cd_entidade"));
                entidade.setNome(rs.getString("nome_entidade"));
                entidade.setEndereco(rs.getString("endereco"));
                entidade.setTelefone(rs.getString("telefone"));
                entidade.setSalasDeAula(rs.getInt("qtde_salas"));
                entidade.setAnoLetivo(rs.getInt("ano_letivo"));
                db.closeConnection();
                return entidade;
            } else {
                db.closeConnection();
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            db.closeConnection();
            return null;
        }
    }

    /**
     * busca todas as entidades cadastradas no sistema
     * @return ArrayList
     */
    public static ArrayList buscarTodos() {
        return buscaPorNome("");
    }

    /**
     * Busca entidades por parâmetros variados e retorna um ArrayList
     * de Value Objects
     * @param campos Map
     * @return ArrayList
     */
    public static ArrayList buscaParametrizada(Map campos) {
        SigeDataBase db = new SigeDataBase();
        StringBuffer sb = new StringBuffer(
                "SELECT cd_entidade FROM entidades WHERE true ");
        int i = 0;
        Object[] params = new Object[campos.size()];
        if (campos.get("nome") != null) {
            params[i++] = (Object) campos.get("nome");
            sb.append("AND lower(nome_entidade) LIKE lower(?) ");
        }
        if (campos.get("endereco") != null) {
            params[i++] = (String) campos.get("endereco");
            sb.append("AND lower(endereco) LIKE lower(?) ");
        }
        if (campos.get("telefone") != null) {
            params[i++] = (String) campos.get("telefone");
            sb.append("AND telefone LIKE ? ");
        }
        if (campos.get("qtdeSalas") != null) {
            params[i++] = (Integer) campos.get("qtdeSalas");
            sb.append("AND qtde_salas=? ");
        } else if (campos.get("qtdeSalasMaior") != null) {
            params[i++] = (Integer) campos.get("qtdeSalasMaior");
            sb.append("AND qtde_salas>=? ");
        } else if (campos.get("qtdeSalasMenor") != null) {
            params[i++] = (Integer) campos.get("qtdeSalasMenor");
            sb.append("AND qtde_salas<=? ");
        }
        String query = new String(sb);
        try {
            db.prepareStatement(query);
            for (int k = 0; k < params.length; k++) {
                if (params[k] instanceof Integer) {
                    db.setInt(k + 1, ((Integer) params[k]).intValue());
                } else {
                    db.setString(k + 1, '%'+(String) params[k]+'%');
                }
            }

            ResultSet rs = db.executeQuery();
            if(rs.next()){
                ArrayList resultado = new ArrayList();
                do{
                    Entidade entidade = buscaPorCodigo(rs.getInt("cd_entidade"));
                    resultado.add(entidade);
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

    /**
     * Busca entidades pelo nome e retorna um ArrayList de Value Objects
     * representando as entidades
     * @param nome String
     * @return ArrayList
     */
    public static ArrayList buscaPorNome(String nome) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.prepareStatement(
                    "SELECT cd_entidade FROM entidades WHERE lower(nome_entidade) LIKE lower(?)");
            db.setString(1, '%' + nome + '%');
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                ArrayList resultado = new ArrayList();
                do {
                    Entidade entidade = EntidadeDAO.buscaPorCodigo(rs.getInt("cd_entidade"));
                    resultado.add(entidade);
                } while (rs.next());
                db.closeConnection();
                return resultado;
            } else {
                db.closeConnection();
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            db.closeConnection();
            return null;
        }
    }

    /**
     * Salva alterações feitas em um Value Entidade no banco de dados
     * @param entidade Entidade
     * @return boolean
     */
    public static boolean salvarAlteracoes(Entidade entidade) {
        SigeDataBase db = new SigeDataBase();
        try {
            String query =
                    "UPDATE entidades SET cd_entidade=?, nome_entidade=?, " +
                    "endereco=?, telefone=?, qtde_salas=?, ano_letivo=? " +
                    "WHERE cd_entidade=?";
            db.prepareStatement(query);
            db.setInt(1, entidade.getCodigo());
            db.setString(2, entidade.getNome());
            db.setString(3, entidade.getEndereco());
            db.setString(4, entidade.getTelefone());
            db.setInt(5, entidade.getSalasDeAula());
            db.setInt(6, entidade.getAnoLetivo());
            db.setInt(7, entidade.getCodigo());
            int resultado = db.executeUpdate();
            db.closeConnection();
            return (resultado > 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
            db.closeConnection();
            return false;
        }
    }

    /**
     * Recebe um Value Entidade e insere seus dados no Banco de Dados
     * @param entidade Entidade
     * @return boolean
     */
    public static boolean incluir(Entidade entidade) {
        SigeDataBase db = new SigeDataBase();
        try {
            String query = "INSERT INTO entidades(nome_entidade," +
                           " endereco, telefone, qtde_salas) VALUES(" +
                           "?,?,?,?)";
            db.prepareStatement(query);
            db.setString(1, entidade.getNome());
            db.setString(2, entidade.getEndereco());
            db.setString(3, entidade.getTelefone());
            db.setInt(4, entidade.getSalasDeAula());
            int resultado = db.executeUpdate();
            db.closeConnection();
            return (resultado > 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
            db.closeConnection();
            return false;
        }
    }

    /**
     * Exclui um registro referente ao Value Entidade passado como parâmetro
     * @param entidade Entidade
     * @return boolean
     */
    public static boolean excluir(Entidade entidade) {
        SigeDataBase db = new SigeDataBase();
        String query = "DELETE FROM entidades WHERE cd_entidade=?";
        try {
            db.prepareStatement(query);
            db.setInt(1, entidade.getCodigo());
            int resultado = db.executeUpdate();
            db.closeConnection();
            return (resultado > 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
            db.closeConnection();
            return false;
        }
    }
}
