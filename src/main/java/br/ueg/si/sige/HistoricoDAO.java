package br.ueg.si.sige;

import java.util.*;

/**
 * 
 * @author silvinha
 */
public class HistoricoDAO {

	public void atualizar(HistoricoEscolar historico) {
		AlunoDAO.salvarAlteracoes(historico.getAluno());

		for (Iterator<Penalidade> iter = historico.getPenalidades().iterator(); iter
				.hasNext();) {
			Penalidade penalidade = (Penalidade) iter.next();
			PenalidadeDAO dao = new PenalidadeDAO();
			if (dao.buscaPorCodigo(penalidade.getCodigo()) != null) {
				dao.salvarAlteracoes(penalidade);
			} else {
				dao.incluir(penalidade);
			}
		}

		for (Iterator<Boletim> iter = historico.getBoletins().iterator(); iter
				.hasNext();) {
			Boletim boletim = (Boletim) iter.next();
			BoletimDAO dao = new BoletimDAO();
			if (dao.buscaPorCodigo(boletim.getCodigo()) != null) {
				dao.salvarAlteracoes(boletim);
			} else {
				dao.incluir(boletim);
			}
		}

		for (Iterator<ItemHistorico> iter = historico.getExcluidos().iterator(); iter
				.hasNext();) {
			Object item = iter.next();
			if (item instanceof Penalidade) {
				new PenalidadeDAO().excluir((Penalidade) item);
			} else if (item instanceof Boletim) {
				new BoletimDAO().excluir((Boletim) item);
			}
		}

	}

	public HistoricoEscolar buscaPorAluno(Aluno aluno) {

		HistoricoEscolar historico = new HistoricoEscolar();
		historico.setAluno(aluno);
		Map<String, Object> campos = new HashMap<String, Object>();

		campos.put("aluno", aluno);

		Set<Boletim> boletins = new BoletimDAO().buscaParametrizada(campos);
		boletins = (boletins == null) ? new HashSet<Boletim>() : boletins;
		Set<Penalidade> penalidades = new PenalidadeDAO()
				.buscaParametrizada(campos);
		penalidades = (penalidades == null) ? new HashSet<Penalidade>()
				: penalidades;

		historico.setPenalidades(penalidades);
		historico.setBoletins(boletins);
		historico.setExcluidos(new HashSet<ItemHistorico>());

		return historico;
	}

}
