package br.ueg.si.sige;

import java.sql.*;
import java.util.ArrayList;
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
public class AlunoDAO {
    
    public static ArrayList<Aluno> buscarTodos() {
        return buscaPorNome("");
    }
    
    public static ArrayList<Aluno> buscaPorNome(String nome) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.prepareStatement(
                    "SELECT cd_aluno FROM alunos WHERE lower(nome_aluno) LIKE lower(?)");
            db.setString(1, '%' + nome + '%');
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                ArrayList<Aluno> resultado = new ArrayList<Aluno>();
                do {
                    Aluno aluno = buscaPorCodigo(rs.getInt("cd_aluno"));
                    resultado.add(aluno);
                } while (rs.next());
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
     * buscaPorCodigo
     *
     * @param codigo int
     * @return Aluno
     */
    public static Aluno buscaPorCodigo(int codigo) {
        SigeDataBase db = new SigeDataBase();
        String query =
                "SELECT * from alunos WHERE cd_aluno=? ORDER BY nome_aluno";
        try {
            db.prepareStatement(query);
            db.setInt(1, codigo);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                Aluno aluno = new Aluno();
                Entidade entidade = EntidadeDAO.buscaPorCodigo(rs.getInt(
                        "cd_entidade"));
                if (entidade != null) {
                    aluno.setEntidade(entidade);
                } else {
                    throw new SQLException("Aluno Nao Existe");
                }
                aluno.setCodigo(rs.getInt("cd_aluno"));
                aluno.setNome(rs.getString("nome_aluno"));
                aluno.setEndereco(rs.getString("endereco"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setPai(rs.getString("nome_pai"));
                aluno.setMae(rs.getString("nome_mae"));
                aluno.setResponsavel(rs.getString("nome_responsavel"));
                aluno.setDataDeNascimento(rs.getDate("data_nascimento"));
                aluno.setDocumentos(rs.getBoolean("documentos_ok"));
                aluno.setNacionalidade(rs.getString("nacionalidade"));
                aluno.setNaturalidade(rs.getString("naturalidade"));
                aluno.setEscolaridade(rs.getInt("escolaridade"));
                aluno.setAtivo(rs.getBoolean("ativo"));
                rs.close();
                db.closeConnection();
                return aluno;
            }
            db.closeConnection();
            
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * incluir
     *
     * @param VO Aluno
     * @return boolean
     */
    public static boolean incluir(Aluno aluno) {
        SigeDataBase db = new SigeDataBase();
        
        String query =
                "INSERT INTO alunos(cd_aluno,cd_entidade,nome_aluno,endereco,telefone,nome_pai,nome_mae,nome_responsavel,data_nascimento,documentos_ok,nacionalidade,naturalidade,ativo,escolaridade)" +
                " VALUES(default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Statement stmt = db.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT last_value as codigo FROM alunos_cd_aluno_seq");
            rs.next();
            
            int codigo = rs.getInt("codigo") + 1;
            aluno.setCodigo(codigo);
            db.getConn().setAutoCommit(false);
            db.prepareStatement(query);
            db.setInt(1, aluno.getEntidade().getCodigo());
            db.setString(2, aluno.getNome());
            db.setString(3, aluno.getEndereco());
            db.setString(4, aluno.getTelefone());
            db.setString(5, aluno.getPai());
            db.setString(6, aluno.getMae());
            db.setString(7, aluno.getResponsavel());
            java.sql.Date dataDeNascimento = new java.sql.Date(aluno.
                    getDataDeNascimento().getTime());
            db.setDate(8, dataDeNascimento);
            db.setBoolean(9, aluno.isDocumentos());
            db.setString(10, aluno.getNacionalidade());
            db.setString(11, aluno.getNaturalidade());
            db.setBoolean(12, aluno.isAtivo());
            db.setInt(13, aluno.getEscolaridade());
            
            db.executeUpdate();
            db.getPreparedStatement().close();
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
     * @param VO Aluno
     * @return boolean
     */
    public static boolean excluir(Aluno aluno) {
        SigeDataBase db = new SigeDataBase();
        
        try {
            db.getConn().setAutoCommit(false);
            db.prepareStatement("DELETE FROM alunos WHERE cd_aluno=?");
            db.setInt(1, aluno.getCodigo());
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
    
    /**
     * salvarAlteracoes
     *
     * @param VO Aluno
     * @return boolean
     */
    public static boolean salvarAlteracoes(Aluno aluno) {
        SigeDataBase db = new SigeDataBase();
        try {
            db.getConn().setAutoCommit(false);
            db.prepareStatement("UPDATE alunos SET cd_aluno=?, " +
                    "cd_entidade=?, nome_aluno=?, endereco=?," +
                    " telefone=?, nome_pai=?, nome_mae=?," +
                    " nome_responsavel=?, data_nascimento=?," +
                    " documentos_ok=?, nacionalidade=?, naturalidade=?," +
                    " ativo=?, escolaridade=? WHERE cd_aluno=?");
            db.setInt(1, aluno.getCodigo());
            db.setInt(2, aluno.getEntidade().getCodigo());
            db.setString(3, aluno.getNome());
            db.setString(4, aluno.getEndereco());
            db.setString(5, aluno.getTelefone());
            db.setString(6, aluno.getPai());
            db.setString(7, aluno.getMae());
            db.setString(8, aluno.getResponsavel());
            java.sql.Date dataDeNascimento = new java.sql.Date(aluno.
                    getDataDeNascimento().getTime());
            db.setDate(9, dataDeNascimento);
            db.setBoolean(10, aluno.isDocumentos());
            db.setString(11, aluno.getNacionalidade());
            db.setString(12, aluno.getNaturalidade());
            db.setBoolean(13, aluno.isAtivo());
            db.setInt(14, aluno.getEscolaridade());
            db.setInt(15, aluno.getCodigo());
            
            db.executeUpdate();
            db.getPreparedStatement().close();
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
    
    public static ArrayList<Aluno> buscaPorEntidade(Entidade entidade){
        SigeDataBase db = new SigeDataBase();
        try{
            
            db.prepareStatement("SELECT cd_aluno FROM alunos WHERE cd_entidade=?");
            db.setInt(1, entidade.getCodigo());
            ResultSet rs = db.executeQuery();
            ArrayList<Aluno> ret = null;
            if(rs.next()){
                ret = new ArrayList<Aluno>();
                do{
                    Aluno aluno = AlunoDAO.buscaPorCodigo(rs.getInt("cd_aluno"));
                    ret.add(aluno);
                }while(rs.next());
            }
            rs.close();
            db.closeConnection();
            return ret;
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Aluno> buscaPorMatriculas(boolean matriculados){
        SigeDataBase db = new SigeDataBase();
        String comp = (matriculados)?"":"not";
        try{
            String query = "SELECT cd_aluno from alunos where cd_aluno "+comp+
                    " in(select cd_aluno from matriculas)";
            db.createStatement();
            ResultSet rs = db.executeQuery(query);
            ArrayList<Aluno> resultado = null;
            if(rs.next()){
                resultado = new ArrayList<Aluno>();
                do{
                    Aluno aluno = AlunoDAO.buscaPorCodigo(rs.getInt("cd_aluno"));
                    resultado.add(aluno);
                }while(rs.next());
            }
            rs.close();
            db.closeConnection();
            return resultado;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Aluno> buscaParametrizada(Map<String, Object> campos) {
        Object[] params = new String[campos.size()];
        int i = 0;
        String q = "SELECT cd_aluno FROM alunos WHERE true ";
        StringBuffer preQuery = new StringBuffer(q);
        
        if (campos.get("nome") != null) {
            preQuery.append(" AND lower(nome_aluno) LIKE lower('%"+
                    (String) campos.get("nome")+"%')");
        }
        
        if (campos.get("nome_pai") != null) {
            preQuery.append(" AND lower(nome_pai) LIKE lower(?)");
            params[i++] = (String) campos.get("nome_pai");
        }
        
        if (campos.get("nome_mae") != null) {
            preQuery.append(" AND lower(nome_mae) LIKE lower(?)");
            params[i++] = (String) campos.get("nome_mae");
        }
        
        if (campos.get("escolaridade") != null) {
            preQuery.append(" AND escolaridade="+(String)campos.get("escolaridade"));
        }
        
        if(campos.get("matriculados")!=null){
            String comp = (String) campos.get("matriculados");
            preQuery.append(" AND cd_aluno "+
                    ((comp.equals("true"))?"":"not")+" in (SELECT " +
                    "m.cd_aluno FROM matriculas as m, turmas as t"+
                    " WHERE t.cd_turma=m.cd_turma AND t.ano=" +
                    ((Entidade)campos.get("entidade")).getAnoLetivo()+")");
        }
        
        if(campos.get("entidade")!=null){
            preQuery.append(" AND cd_entidade="+((Entidade)campos.get("entidade")).getCodigo());
        }
        String query = new String(preQuery);
        System.out.println(query);
        SigeDataBase db = new SigeDataBase();
        try {
            db.createStatement();
//            Set<String> keys = campos.keySet();
            i = 0;
            ResultSet rs = db.executeQuery(query);
            ArrayList<Aluno> resultado = null;
            if (rs.next()) {
                resultado = new ArrayList<Aluno>();
                do {
                    Aluno aluno = AlunoDAO.buscaPorCodigo(rs.getInt("cd_aluno"));
                    resultado.add(aluno);
                } while (rs.next());
            }
            rs.close();
            db.closeConnection();
            return resultado;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
