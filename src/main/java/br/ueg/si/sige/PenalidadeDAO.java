package br.ueg.si.sige;

import java.sql.*;
import java.util.*;

/**
 * 
 * @author silvinha
 */
public class PenalidadeDAO {

	public boolean incluir(Penalidade penalidade) {

		SigeDataBase db = new SigeDataBase();
		try {
			db.prepareStatement("INSERT INTO penalidades (tipo_penalidade, "
					+ "descricao, cd_matricula) VALUES(?,?,?)");
			db.setString(1, penalidade.getTipo());
			db.setString(2, penalidade.getDescricao());
			db.setInt(3, penalidade.getMatricula().getCodigo());

			int i = db.executeUpdate();
			db.closeConnection();
			return i > 0;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		db.closeConnection();
		return false;
	}

	public boolean salvarAlteracoes(Penalidade penalidade) {
		SigeDataBase db = new SigeDataBase();

		try {
			db.prepareStatement("UPDATE penalidades SET tipo_penalidade=?, descricao=?, cd_matricula=? WHERE cd_penalidade=");
			db.setString(1, penalidade.getTipo());
			db.setString(2, penalidade.getDescricao());
			db.setInt(3, penalidade.getMatricula().getCodigo());
			db.setInt(4, penalidade.getCodigo());

			int i = db.executeUpdate();
			db.closeConnection();
			return i > 0;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		db.closeConnection();
		return false;

	}

	public boolean excluir(Penalidade penalidade) {

		SigeDataBase db = new SigeDataBase();

		try {
			db.prepareStatement("DELETE FROM penalidades WHERE cd_penalidade =?");
			db.setInt(1, penalidade.getCodigo());
			int i = db.executeUpdate();
			db.closeConnection();
			return i > 0;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		db.closeConnection();
		return false;
	}

	public Penalidade buscaPorCodigo(int codigo) {

		SigeDataBase db = new SigeDataBase();

		try {
			db.prepareStatement("SELECT * FROM penalidades WHERE cd_penalidade=?");
			db.setInt(1, codigo);

			ResultSet rs = db.executeQuery();
			Penalidade penalidade = null;

			if (rs.next()) {
				penalidade = new Penalidade();
				penalidade.setCodigo(rs.getInt("cd_penalidade"));
				penalidade.setTipo(rs.getString("tipo_penalidade"));
				penalidade.setDescricao(rs.getString("descricao"));
				penalidade.setMatricula(new MatriculaDAO().buscaPorCodigo(rs
						.getInt("cd_matricula")));
			}
			rs.close();
			db.closeConnection();
			return penalidade;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		db.closeConnection();
		return null;
	}

	public Set<Penalidade> buscaParametrizada(Map<String, Object> campos) {

		SigeDataBase db = new SigeDataBase();

		String query = "SELECT cd_penalidade FROM penalidades WHERE true ";

		if (campos.get("tipo") != null) {
			query += "AND tipo_penalidade=" + ((String) campos.get("tipo"));
		}

		if (campos.get("aluno") != null) {
			query += " AND cd_penalidade in(SELECT cd_penalidade FROM "
					+ "penalidades as p, matriculas as m WHERE p.cd_matricula"
					+ "=m.cd_matricula AND m.cd_aluno="
					+ ((Aluno) campos.get("aluno")).getCodigo() + ")";
		}

		query += " ORDER BY tipo_penalidade";
		try {
			db.createStatement();
			ResultSet rs = db.executeQuery(query);
			Set<Penalidade> resultado = null;

			if (rs.next()) {
				resultado = new HashSet<Penalidade>();

				do {
					Penalidade penalidade = this.buscaPorCodigo(rs
							.getInt("cd_penalidade"));
					resultado.add(penalidade);
				} while (rs.next());
			}
			rs.close();
			db.closeConnection();
			return resultado;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		db.closeConnection();
		return null;
	}
}
