package br.ueg.si.sige;

import java.io.Serializable;
import java.util.Set;

public class Boletim implements Serializable {
	private static final long serialVersionUID = 2889965759373967365L;
	private int codigo;
	private int anoLetivo;
	private Turma turma;
	private Entidade entidade;
	private Matricula matricula;
	private Set<ItemBoletim> itensBoletim;

	public Boletim() {
		//
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(int anoLetivo) {
		this.anoLetivo = anoLetivo;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Set<ItemBoletim> getItensBoletim() {
		return itensBoletim;
	}

	public void setItensBoletim(Set<ItemBoletim> itensBoletim) {
		this.itensBoletim = itensBoletim;
	}

}
