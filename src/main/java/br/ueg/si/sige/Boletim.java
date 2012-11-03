package br.ueg.si.sige;

import java.util.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.*;

public class Boletim implements Serializable{
    
    private int codigo;
    private int anoLetivo;
    private Turma turma;
    private Entidade entidade;
    private Matricula matricula;
    private Set itensBoletim;
    
  
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

    public Set getItensBoletim() {
        return itensBoletim;
    }

    public void setItensBoletim(Set itensBoletim) {
        this.itensBoletim = itensBoletim;
    }
    
}
