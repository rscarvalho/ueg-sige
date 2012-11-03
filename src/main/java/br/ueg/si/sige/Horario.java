package br.ueg.si.sige;

import java.util.*;

/**
 *
 *
 * @author silvinha
 */

public class Horario implements java.io.Serializable{
    
    private int codigo;
    private Turma turma;
    private Set itensHorario;  

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

    public Set getItensHorario() {
        return itensHorario;
    }

    public void setItensHorario(Set itensHorario) {
        this.itensHorario = itensHorario;
    }
    
    
    
}
