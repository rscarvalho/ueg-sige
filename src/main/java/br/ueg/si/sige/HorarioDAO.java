package br.ueg.si.sige;

import java.sql.*;
import java.util.*;

/**
 * 
 * @author silvinha
 */
public class HorarioDAO {

	public boolean incluir(Horario horario) {
		SigeDataBase db = new SigeDataBase();
		try {
			db.prepareStatement("INSERT INTO horarios (cd_turma) VALUES(?)");
			db.setInt(1, horario.getTurma().getCodigo());

			int i = db.executeUpdate();

			if (i > 0) {
				db.createStatement();
				ResultSet rs = db
						.executeQuery("SELECT last_value from horarios_cd_horario_seq");
				rs.next();
				horario.setCodigo(rs.getInt("last_value"));

				db.prepareStatement("INSERT INTO itenshorario "
						+ "(cd_horario,cd_disciplina,dia_da_semana,numero_aula) VALUES(?,?,?,?)");
				for (Iterator<ItemHorario> iter = horario.getItensHorario()
						.iterator(); iter.hasNext();) {
					ItemHorario item = (ItemHorario) iter.next();
					db.setInt(1, horario.getCodigo());
					db.setInt(2, item.getDisciplina().getCodigo());
					db.setInt(3, item.getDiaDaSemana());
					db.setInt(4, (int) item.getNumeroDaAula());
					db.executeUpdate();
				}
				rs.close();
				db.getStatement().close();
				db.closeConnection();

			} else {
				return false;
			}
			db.closeConnection();
			return i > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		db.closeConnection();
		return false;
	}

	public boolean excluir(Horario horario) {
		SigeDataBase db = new SigeDataBase();
		try {
			db.getConn().setAutoCommit(false);
			db.prepareStatement("DELETE FROM itenshorario WHERE cd_horario=?");
			db.setInt(1, horario.getCodigo());
			db.executeUpdate();
			db.getPreparedStatement().close();

			db.prepareStatement("DELETE FROM horarios WHERE cd_horario=?");
			db.setInt(1, horario.getCodigo());
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

	public boolean salvarAlteracoes(Horario horario) {

		SigeDataBase db = new SigeDataBase();
		try {
			db.getConn().setAutoCommit(false);

			db.prepareStatement("UPDATE horarios SET cd_turma=? WHERE cd_horario=?");
			db.setInt(1, horario.getCodigo());
			db.setInt(2, horario.getTurma().getCodigo());

			db.executeUpdate();
			db.getPreparedStatement().close();

			// exclui os itens antigos
			db.prepareStatement("DELETE FROM itenshorario WHERE cd_horario=?");
			db.setInt(1, horario.getCodigo());
			db.executeUpdate();
			db.getPreparedStatement().close();
			// inclui os novos itens
			db.prepareStatement("INSERT INTO itenshorario (cd_horario, cd_disciplina, "
					+ "dia_da_semana, numero_aula) VALUES(?,?,?,?)");

			for (Iterator<ItemHorario> iter = horario.getItensHorario()
					.iterator(); iter.hasNext();) {
				ItemHorario item = (ItemHorario) iter.next();
				db.setInt(1, horario.getCodigo());
				db.setInt(2, item.getDisciplina().getCodigo());
				db.setInt(3, item.getDiaDaSemana());
				db.setInt(4, item.getNumeroDaAula());
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

	public Horario buscaPorCodigo(int codigo) {

		SigeDataBase db = new SigeDataBase();

		try {
			Horario horario = new Horario();
			db.prepareStatement("SELECT * FROM horarios WHERE cd_horario = ?");
			db.setInt(1, codigo);

			ResultSet rs = db.executeQuery();

			if (rs.next()) {

				horario.setCodigo(rs.getInt("cd_horario"));
				horario.setTurma(new TurmaDAO().buscaPorCodigo(rs
						.getInt("cd_turma")));
				horario.setItensHorario(new HashSet<ItemHorario>());
				rs.close();

				db.prepareStatement("SELECT * FROM itens_horario WHERE cd_horario=?");
				db.setInt(1, horario.getCodigo());
				rs = db.executeQuery();

				while (rs.next()) {
					ItemHorario item = new ItemHorario();
					db.setInt(1, codigo);
					while (rs.next()) {
						item.setCodigo(rs.getInt("cd_item"));
						item.setNumeroDaAula(rs.getInt("numero_aula"));
						item.setDiaDaSemana(rs.getInt("dia_da_semana"));
						item.setDisciplina(new DisciplinaDAO()
								.buscaPorCodigo(rs.getInt("cd_disciplina")));

						horario.getItensHorario().add(item);
					}
					rs.close();
					db.closeConnection();
					return horario;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public Set<Horario> buscaParametrizada(Map<String, Object> campos) {
		SigeDataBase db = new SigeDataBase();

		try {
			String query = "SELECT cd_horario FROM horarios WHERE true";

			if (campos.get("turma") != null) {
				Turma turma = (Turma) campos.get("turma");
				query += " AND cd_turma=" + (turma.getCodigo());
			}

			if (campos.get("entidade") != null) {
				Entidade entidade = (Entidade) campos.get("entidade");
				query += " AND cd_entidade=" + (entidade.getCodigo());
			}

			db.createStatement();
			ResultSet rs = db.executeQuery(query);

			if (rs.next()) {
				Set<Horario> resultado = new HashSet<Horario>();
				while (rs.next()) {
					Horario horario = this.buscaPorCodigo(rs
							.getInt("cd_horario"));
					resultado.add(horario);
				}
				rs.close();
				db.closeConnection();
				return resultado;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		db.closeConnection();
		return null;

	}
}
