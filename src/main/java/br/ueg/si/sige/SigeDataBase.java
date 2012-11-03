package br.ueg.si.sige;

import java.sql.*;

/**
 * <p>Title: </p>
 *
 * <p>Descricao: Classe para manipulacao do banco de dados do SIGE</p>
 *
 * @author Rodolfo S. Carvalho
 * @version 1.0
 */
public class SigeDataBase {

    private Connection conn;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private String lastQuery;

    /**
     *
     * @param strCnn String
     * @param user String
     * @param password String
     */
    public SigeDataBase(String strCnn, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(strCnn, user, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Classe não Encontrada: " + ex.toString());
        } catch (SQLException ex) {
            System.out.println("Erro de SQL: " + ex.toString());
        }

    }

    /**
     * SigeDataBase
     */
    public SigeDataBase() {
        this("jdbc:postgresql://127.0.0.1/sige","sige","sige");
    }

    /**
     *
     * @throws SQLException
     */
    public void createStatement() throws SQLException {
        statement = conn.createStatement();
    }

    /**
     *
     * @param statement String
     * @throws SQLException
     */
    public void prepareStatement(String statement) throws SQLException {
        lastQuery = statement;
        preparedStatement = conn.prepareStatement(statement);
    }

    /**
     *
     * @param parameterIndex int
     * @param parameter String
     * @throws SQLException
     */
    public void setString(int parameterIndex, String parameter) throws
            SQLException {
        preparedStatement.setString(parameterIndex, parameter);
    }

    /**
     *
     * @param parameterIndex int
     * @param parameter int
     * @throws SQLException
     */
    public void setInt(int parameterIndex, int parameter) throws SQLException {
        preparedStatement.setInt(parameterIndex, parameter);
    }

    /**
     *
     * @param parameterIndex int
     * @param parameter boolean
     * @throws SQLException
     */
    public void setBoolean(int parameterIndex, boolean parameter) throws
            SQLException {
        preparedStatement.setBoolean(parameterIndex, parameter);
    }

    /**
     *
     * @param parameterIndex int
     * @param parameter Date
     * @throws SQLException
     */
    public void setDate(int parameterIndex, Date parameter) throws
            SQLException {
        preparedStatement.setDate(parameterIndex, parameter);
    }

    /**
     *
     * @param parameterIndex int
     * @param parameter float
     * @throws SQLException
     */
    public void setFloat(int parameterIndex, float parameter) throws
            SQLException {
        preparedStatement.setFloat(parameterIndex, parameter);
    }

    /**
     *
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet executeQuery() throws SQLException {
        return preparedStatement.executeQuery();
    }

    /**
     *
     * @return int
     * @throws SQLException
     */
    public int executeUpdate() throws SQLException {
        return preparedStatement.executeUpdate();
    }

    /**
     *
     * @param query String
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException {
        lastQuery = query;
        return statement.executeQuery(query);
    }

    /**
     *
     * @param query String
     * @return int
     * @throws SQLException
     */
    public int executeUpdate(String query) throws SQLException {
        lastQuery = query;
        return statement.executeUpdate(query);
    }

    /**
     *
     * @throws SQLException
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sobrescreve finalize() de java.lang.Object
     * @see java.lang.Object#finalize()
     */
    public void finalize() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Erro de SQL: " + ex.toString());
        }
    }

    /**
     * Sobrescreve toString() de java.lang.Object
     * @see java.lang.Object#toString()
     * @return String
     */
    public String toString(){
        String ret = "Conexão: "+conn.toString()+"\n";
        return ret;
    }

    public Connection getConn() {
        return conn;
    }

    public String getLastQuery() {
        return lastQuery;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
