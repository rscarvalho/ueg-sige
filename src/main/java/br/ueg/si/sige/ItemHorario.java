package br.ueg.si.sige;

/**
 * 
 * @author silvinha
 */
public class ItemHorario implements java.io.Serializable {
	private static final long serialVersionUID = -3923281299861761557L;
	private int codigo;
	private int numeroDaAula;
	private int diaDaSemana;
	private Horario horario;
	private Disciplina disciplina;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getNumeroDaAula() {
		return numeroDaAula;
	}

	public void setNumeroDaAula(int numeroDaAula) {
		this.numeroDaAula = numeroDaAula;
	}

	public int getDiaDaSemana() {
		return diaDaSemana;
	}

	public void setDiaDaSemana(int diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

}
