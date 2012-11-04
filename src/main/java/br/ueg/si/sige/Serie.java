package br.ueg.si.sige;

import java.util.Set;

public class Serie implements java.io.Serializable{
	private static final long serialVersionUID = -1345915613731224752L;
	private int codigo;    
    private int numero;    
    private Set<Disciplina> disciplinas;    
    private int vagas;
    
    
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }
    
    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }
    
}

