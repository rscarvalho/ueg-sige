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
public class UsuarioDAO {
    public static Usuario buscaPorLoginSenha(String login, String senha) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.prepareStatement(
                    "SELECT cd_usuario FROM usuarios WHERE login = ? AND senha = encode('"+senha+"','base64')");
            db.setString(1, login);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                Usuario usuario = buscaPorCodigo(rs.getInt("cd_usuario"));
                rs.close();
                db.closeConnection();
                return usuario;
            }
            rs.close();
            db.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * buscaPorCodigo
     *
     * @param codigo int
     * @return Usuario
     */
    public static Usuario buscaPorCodigo(int codigo) {
        SigeDataBase db = new SigeDataBase();

        try {
            String query = "SELECT cd_usuario as codigo, ativo, nome_usuario " +
                           "as nome, cd_entidade as entidade, login," +
                           " decode(senha,'base64') as senha, cpf FROM usuarios" +
                           " WHERE cd_usuario=? ORDER BY nome_usuario";
            db.prepareStatement(query);
            db.setInt(1, codigo);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                Entidade entidade = EntidadeDAO.buscaPorCodigo(rs.getInt(
                        "entidade"));
                if (entidade != null) {
                    usuario.setEntidade(entidade);
                } else {
                    throw new SQLException("Entidade Nao Existe");
                }
                usuario.setCodigo(rs.getInt("codigo"));
                usuario.setNomeCompleto(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setCPF(rs.getString("cpf"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuario.setPermissoes(PermissaoDAO.buscaPorUsuario(
                        usuario));
                rs.close();
                db.closeConnection();
                return usuario;
            }
            db.closeConnection();
            return null;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * buscarTodos
     *
     * @param nome String
     * @return ArrayList
     */
    public static ArrayList buscarTodos() {
        return buscaPorNome("");
    }

    /**
     * buscaPorLogin
     *
     * @param nome String
     * @return ArrayList
     */
    public static ArrayList buscaPorLogin(String login) {
        SigeDataBase db = new SigeDataBase();
        try {
            String query = "SELECT cd_usuario as codigo, nome_usuario as nome," +
                           " cd_entidade as entidade, login, decode(senha,'base64')" +
                           " as senha, cpf, ativo FROM usuarios WHERE lower(login) LIKE lower(?) ORDER BY nome_usuario";
            db.setPreparedStatement(db.getConn().prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)
                    );
            db.setString(1, '%' + login + '%');
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                ArrayList resultado = new ArrayList();
                do {
                    Usuario usuario = new Usuario();
                    Entidade entidade = (Entidade)
                                        EntidadeDAO.buscaPorCodigo(rs.getInt(
                                                "entidade"));
                    if (entidade != null) {
                        usuario.setEntidade(entidade);
                    } else {
                        throw new SQLException("Entidade Não Existe");
                    }
                    usuario.setCodigo(rs.getInt("codigo"));
                    usuario.setNomeCompleto(rs.getString("nome"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setCPF(rs.getString("cpf"));
                    usuario.setPermissoes(PermissaoDAO.buscaPorUsuario(
                            usuario));
                    usuario.setAtivo(rs.getBoolean("ativo"));
                    resultado.add(usuario);
                } while (rs.next());
                rs.close();
                db.closeConnection();
                return resultado;
            }
            db.closeConnection();
            return null;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * buscaPorNome
     *
     * @param nome String
     * @return ArrayList
     */
    public static ArrayList buscaPorNome(String nome) {
        SigeDataBase db = new SigeDataBase();

        try {
            String query = "SELECT cd_usuario as codigo, nome_usuario as nome, cd_entidade as entidade, login, decode(senha,'base64') as senha, cpf, ativo FROM usuarios WHERE lower(nome_usuario) LIKE lower(?) AND cd_usuario!=1 ORDER BY nome_usuario";
            db.setPreparedStatement(db.getConn().prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)
                    );
            db.setString(1, '%' + nome + '%');
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                ArrayList resultado = new ArrayList();
                do {
                    Usuario usuario = new Usuario();
                    Entidade entidade = (Entidade)
                                        EntidadeDAO.buscaPorCodigo(rs.getInt(
                                                "entidade"));
                    if (entidade != null) {
                        usuario.setEntidade(entidade);
                    } else {
                        throw new SQLException("Entidade Não Existe");
                    }
                    usuario.setCodigo(rs.getInt("codigo"));
                    usuario.setNomeCompleto(rs.getString("nome"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setCPF(rs.getString("cpf"));
                    usuario.setPermissoes(PermissaoDAO.buscaPorUsuario(
                            usuario));
                    usuario.setAtivo(rs.getBoolean("ativo"));
                    resultado.add(usuario);
                } while (rs.next());
                rs.close();
                db.closeConnection();
                return resultado;
            }
            db.closeConnection();
            return null;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * buscaParametrizada
     *
     * @param nome String
     * @return ArrayList
     */
    public static ArrayList buscaParametrizada(Map campos) {
        SigeDataBase db = new SigeDataBase();
        String query =
                "SELECT cd_usuario FROM permissao_por_usuario WHERE lower(nome_usuario)" +
                " LIKE lower(?) AND lower(login) LIKE lower(?) AND cpf LIKE ? AND cd_usuario!=1";
        StringBuffer preQuery = new StringBuffer(query);
        if (campos.get("entidade") != null) {
            preQuery.append(" AND cd_entidade=?");
        }
        ArrayList permissoes = (ArrayList) campos.get("permissoes");
        if (permissoes != null) {
            preQuery.append("AND (");
            for (Iterator iter = permissoes.iterator(); iter.hasNext(); ) {
                Permissao permissao = (Permissao) iter.next();
                preQuery.append(" cd_permissao=?");
                if (iter.hasNext()) {
                    preQuery.append(" OR");
                }
            }
            preQuery.append(" )");
        }
        String status = (String) campos.get("status");
        if (!status.equals("both")) {
            preQuery.append(" AND ativo=?");
        }
        preQuery.append("GROUP BY cd_usuario");
        query = new String(preQuery);
        try {
            int i = 1;
            db.prepareStatement(query);
            db.setString(i++,
                         '%' + (String) campos.get("nomeCompleto") + '%');
            db.setString(i++, '%' + (String) campos.get("login") + '%');
            db.setString(i++, '%' + (String) campos.get("cpf") + '%');
            if (campos.get("entidade") != null) {
                Entidade entidade = (Entidade) campos.get("entidade");
                db.setInt(i++, entidade.getCodigo());
            }
            if (permissoes != null) {
                for (Iterator iter = permissoes.iterator(); iter.hasNext(); ) {
                    Permissao permissao = (Permissao) iter.next();
                    db.setInt(i++, permissao.getCodigo());
                }
            }
            if (!status.equals("both")) {
                db.setBoolean(i++, status.equals("ativo"));
            }
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                ArrayList resultado = new ArrayList();
                do {
                    Usuario usuario = buscaPorCodigo(rs.getInt("cd_usuario"));
                    resultado.add(usuario);
                } while (rs.next());
                rs.close();
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

    /**
     * salvarAlteracoes
     *
     * @param VO Usuario
     * @return boolean
     */
    public static boolean salvarAlteracoes(Usuario usuario) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.getConn().setAutoCommit(false);
            String query = "UPDATE usuarios SET cd_usuario=?, cd_entidade=?, nome_usuario=?, login=?, cpf=?, ativo=?";
            if (usuario.getSenha() != null) {
                query += ", senha=encode('"+usuario.getSenha()+"','base64')";
            }
            query += " WHERE cd_usuario=?";
            db.prepareStatement(query);
            db.setInt(1, usuario.getCodigo());
            db.setInt(2, usuario.getEntidade().getCodigo());
            db.setString(3, usuario.getNomeCompleto());
            db.setString(4, usuario.getLogin());
            db.setString(5, usuario.getCPF());
            db.setBoolean(6, usuario.isAtivo());
            db.setInt(7, usuario.getCodigo());
            db.executeUpdate();
            db.getPreparedStatement().close();
            //exclui as permissões anteriores
            db.prepareStatement(
                    "DELETE FROM permissao_usuario WHERE cd_usuario=?");
            db.setInt(1, usuario.getCodigo());
            db.executeUpdate();
            db.getPreparedStatement().close();
            //inclui as novas permissões
            db.prepareStatement(
                    "INSERT INTO permissao_usuario(cd_usuario, cd_permissao)" +
                    " VALUES(?,?)");
            for (int i = 0; i < usuario.getPermissoes().length; i++) {
                db.setInt(1, usuario.getCodigo());
                db.setInt(2, usuario.getPermissoes()[i].getCodigo());
                db.executeUpdate();
            }
            db.getConn().commit();
            db.closeConnection();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                db.getConn().rollback();
                db.closeConnection();
            } catch (SQLException ex1) {
                db.closeConnection();
                ex1.printStackTrace();
            }
        }
        return false;
    }

    /**
     * incluir
     *
     * @param VO Usuario
     * @return boolean
     */
    public static boolean incluir(Usuario usuario) {
        SigeDataBase db = new SigeDataBase();

        String query =
                "INSERT INTO usuarios(cd_usuario,cd_entidade,nome_usuario,cpf,login,senha,ativo)" +
                " VALUES(default, ?, ?, ?, ?, encode( ?,'base64'), ?)";

        try {
            Statement stmt = db.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT last_value as codigo FROM usuarios_cd_usuario_seq");
            rs.next();
            int codigo = rs.getInt("codigo") + 1;
            usuario.setCodigo(codigo);
            db.getConn().setAutoCommit(false);
            db.prepareStatement(query);
            db.setInt(1, usuario.getEntidade().getCodigo());
            db.setString(2, usuario.getNomeCompleto());
            db.setString(3, usuario.getCPF());
            db.setString(4, usuario.getLogin());
            if (usuario.getSenha() != null) {
                db.setString(5, usuario.getSenha());
            } else {
                db.setString(5, "");
            }
            db.setBoolean(6, usuario.isAtivo());
            db.executeUpdate();
            db.getPreparedStatement().close();

            db.prepareStatement(
                    "INSERT INTO permissao_usuario(cd_usuario, cd_permissao) VALUES(?,?)");
            for (int i = 0; i < usuario.getPermissoes().length; i++) {
                db.setInt(1, usuario.getCodigo());
                db.setInt(2, usuario.getPermissoes()[i].getCodigo());
                db.executeUpdate();
            }
            db.getConn().commit();
            db.closeConnection();
            return true;

        } catch (SQLException ex) {

            try {
                db.getConn().rollback();
                db.closeConnection();
                ex.printStackTrace();
            } catch (SQLException ex1) {
                System.out.println("Erro de SQL: " + ex1.getMessage());
                ex1.printStackTrace();
            }
            return false;
        }
    }

    /**
     * excluir
     *
     * @param VO Usuario
     * @return boolean
     */
    public static boolean excluir(Usuario usuario) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.getConn().setAutoCommit(false);
            db.prepareStatement(
                    "DELETE FROM permissao_usuario WHERE cd_usuario=?");
            db.setInt(1, usuario.getCodigo());
            db.executeUpdate();
            db.getPreparedStatement().close();

            db.prepareStatement("DELETE FROM usuarios WHERE cd_usuario=?");
            db.setInt(1, usuario.getCodigo());
            db.executeUpdate();
            db.getConn().commit();
            db.closeConnection();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                db.getConn().rollback();
                db.closeConnection();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                db.closeConnection();
            }
            return false;
        }
    }
}
