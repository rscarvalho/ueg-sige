package br.ueg.si.sige;

import java.sql.*;
import java.util.*;

/**
 *
 * @author silvinha
 */
public class BoletimDAO {
    
    public boolean incluir(Boletim boletim) {
        SigeDataBase db = new SigeDataBase();
        try{
            db.prepareStatement("INSERT INTO boletins " +
                    "(ano_letivo,cd_turma,cd_entidade,cd_matricula) VALUES(?,?,?,?)");
            db.setInt(1, boletim.getAnoLetivo());
            db.setInt(2, boletim.getTurma().getCodigo());
            db.setInt(3, boletim.getEntidade().getCodigo());
            db.setInt(4, boletim.getMatricula().getCodigo());
            
            int i = db.executeUpdate();
            
            if (i>0){
                db.createStatement();
                ResultSet rs = db.executeQuery("SELECT last_value from boletins_cd_boletim_seq");
                rs.next();
                boletim.setCodigo(rs.getInt("last_value"));
                
                db.prepareStatement("INSERT INTO itensboletim " +
                        "(cd_boletim,cd_disciplina,bimes_letivo,nota,faltas) VALUES(?,?,?,?,?)");
                
                for (Iterator<ItemBoletim> iter = boletim.getItensBoletim().iterator();iter.hasNext();){
                    ItemBoletim item = (ItemBoletim) iter.next();
                    db.setInt(1, boletim.getCodigo());
                    db.setInt(2, item.getDisciplina().getCodigo());
                    db.setInt(3, item.getBimesLetivo());
                    db.setInt(4, (int)item.getNota());
                    db.setInt(5, item.getFaltas());
                    db.executeUpdate();
                }
                rs.close();
                db.getStatement().close();
                db.closeConnection();
                
            }else{
                return false;
            }
            db.closeConnection();
            return i > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        db.closeConnection();
        return false;
    }
    
    public boolean excluir(Boletim boletim){
        
        SigeDataBase db = new SigeDataBase();
        try{
            db.getConn().setAutoCommit(false);
            db.prepareStatement(
                    "DELETE FROM itensboletim WHERE cd_boletim=?");
            db.setInt(1, boletim.getCodigo());
            db.executeUpdate();
            db.getPreparedStatement().close();
            
            db.prepareStatement("DELETE FROM boletins WHERE cd_boletim=?");
            db.setInt(1, boletim.getCodigo());
            db.executeUpdate();
            db.getConn().commit();
            db.closeConnection();
            return true;
        }catch(SQLException ex){
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
    
    public boolean salvarAlteracoes(Boletim boletim){
        
        SigeDataBase db = new SigeDataBase();
        
        try {
            db.getConn().setAutoCommit(false);
            
            db.prepareStatement("UPDATE boletins SET ano_letivo=?, cd_turma=?, " +
                    "cd_entidade=?, cd_matricula=? WHERE cd_boletim=?");
            db.setInt(1, boletim.getCodigo());
            db.setInt(2, boletim.getAnoLetivo());
            db.setInt(3, boletim.getTurma().getCodigo());
            db.setInt(4, boletim.getEntidade().getCodigo());
            db.setInt(5, boletim.getMatricula().getCodigo());
            
            db.executeUpdate();
            db.getPreparedStatement().close();
            
            //exclui os itens antigos
            db.prepareStatement("DELETE FROM itensboletim WHERE cd_boletim=?");
            db.setInt(1, boletim.getCodigo());
            db.executeUpdate();
            db.getPreparedStatement().close();
            //inclui os novos itens
            db.prepareStatement("INSERT INTO itensboletim (cd_boletim, cd_disciplina, " +
                    "bimes_letivo, nota, faltas) VALUES(?,?,?,?,?)");
            
            for (Iterator<ItemBoletim> iter = boletim.getItensBoletim().iterator();iter.hasNext();){
                ItemBoletim item = (ItemBoletim) iter.next();
                db.setInt(1, boletim.getCodigo());
                db.setInt(2, item.getDisciplina().getCodigo());
                db.setInt(3, item.getBimesLetivo());
                db.setInt(4, (int)item.getNota());
                db.setInt(5, item.getFaltas());
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
    
    
    public Boletim buscaPorCodigo(int codigo){
        
        SigeDataBase db = new SigeDataBase();
        
        try{
            Boletim boletim = new Boletim();
            db.prepareStatement("SELECT * FROM boletins WHERE cd_boletim = ?");
            db.setInt(1, codigo);
            
            ResultSet rs = db.executeQuery();
            
            if (rs.next()){
                
                boletim.setCodigo(rs.getInt("cd_boletim"));
                boletim.setAnoLetivo(rs.getInt("ano_letivo"));
                boletim.setTurma(new TurmaDAO().buscaPorCodigo(rs.getInt("cd_turma")));
                boletim.setEntidade(EntidadeDAO.buscaPorCodigo(rs.getInt("cd_entidade")));
                boletim.setMatricula(new MatriculaDAO().buscaPorCodigo(rs.getInt("cd_matricula")));
                boletim.setItensBoletim(new HashSet<ItemBoletim>());
                rs.close();
                
                db.prepareStatement("SELECT * FROM itensboletim WHERE cd_boletim=?");
                db.setInt(1, boletim.getCodigo());
                rs = db.executeQuery();
                
                while (rs.next()){
                        ItemBoletim item = new ItemBoletim();
                        item.setCodigo(rs.getInt("cd_item"));
                        item.setNota(rs.getFloat("nota"));
                        item.setFaltas(rs.getInt("faltas"));
                        item.setBimesLetivo(rs.getInt("bimes_letivo"));
                        item.setDisciplina(new DisciplinaDAO().buscaPorCodigo(rs.getInt("cd_disciplina")));
                        
                        boletim.getItensBoletim().add(item);
                }
                rs.close();
                db.closeConnection();
                return boletim;
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
    
    
    public Set<Boletim> buscaParametrizada(Map<String, Object> campos){
        
        SigeDataBase db = new SigeDataBase();
        
        try{
            String query = "SELECT cd_boletim FROM boletins WHERE true";
            
            if (campos.get("turma") != null){
                Turma turma = (Turma) campos.get("turma");
                query += " AND cd_turma=" + (turma.getCodigo());
            }
            
            if (campos.get("matricula") != null){
                Matricula matricula = (Matricula) campos.get("matricula");
                query += " AND cd_matricula=" + (matricula.getCodigo());
            }
            
            if (campos.get("entidade") != null){
                Entidade entidade = (Entidade) campos.get("entidade");
                query += " AND cd_entidade=" + (entidade.getCodigo());
            }
            
            if (campos.get("anoLetivo") != null){
                query += " AND ano_letivo=" + ((String)campos.get("anoLetivo"));
            }
            
            if(campos.get("aluno")!=null){
                query += " AND cd_matricula in(SELECT cd_matricula from " +
                        "matriculas WHERE cd_aluno="+((Aluno)campos.get("aluno"))
                        .getCodigo()+")";
            }
            
            db.createStatement();
            ResultSet rs = db.executeQuery(query);
            
            if (rs.next()){
                Set<Boletim> resultado = new HashSet<Boletim>();
                do{
                    Boletim boletim = this.buscaPorCodigo(rs.getInt("cd_boletim"));
                    resultado.add(boletim);
                }while(rs.next());
                rs.close();
                db.closeConnection();
                return resultado;
            }
            
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        db.closeConnection();
        return null;
    }
}
