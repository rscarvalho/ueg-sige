package br.ueg.si.sige;

import java.util.*;

/**
 *
 *
 * @author silvinha
 */

public class Horario implements java.io.Serializable{
	private static final long serialVersionUID = -6212001062401544613L;
	private int codigo;
    private Turma turma;
    private Set<ItemHorario> itensHorario;  

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Set<ItemHorario> getItensHorario() {
        return itensHorario;
    }

    public void setItensHorario(Set<ItemHorario> itensHorario) {
        this.itensHorario = itensHorario;
    }
    
    
    
}
