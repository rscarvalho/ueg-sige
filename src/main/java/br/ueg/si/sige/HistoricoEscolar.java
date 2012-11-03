package br.ueg.si.sige;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author silvinha
 */
public class HistoricoEscolar implements Serializable{
    private Aluno aluno;
    private Set boletins;
    private Set penalidades;
    private Set excluidos;
    public int sequenciaPenalidade = 0;
    public int sequenciaBoletim = 0;
    
    public HistoricoEscolar(){
        sequenciaBoletim = 0;
        sequenciaPenalidade = 0;
    }
    
    public void inserirPenalidade(Penalidade penalidade){
        getPenalidades().add(penalidade);
    }
    
    public void inserirBoletim(Boletim boletim){
        getBoletins().add(boletim);
    }
    
    public void removerPenalidade(Penalidade penalidade){
        for(Iterator iter = penalidades.iterator();iter.hasNext();){
            if(((Penalidade)iter.next()).getCodigo()==penalidade.getCodigo()){
                iter.remove();
                break;
            }
        }
    }
    
    public Penalidade buscaPenalidadePorCodigo(int codigo){
        for(Iterator iter = penalidades.iterator();iter.hasNext();){
            Penalidade item = (Penalidade)iter.next();
            if(item.getCodigo()==codigo){
                return item;
            }
        }
        return null;
    }
    
    public void removerBoletim(Boletim boletim){
        getBoletins().remove(boletim);
    }
    
    public void atualizarBoletim(Boletim boletim){
        if(getBoletins().contains(boletim)){
            removerBoletim(boletim);
        }
        inserirBoletim(boletim);
    }
    
    public Aluno getAluno() {
        return aluno;
    }
    
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    
    public Set getBoletins() {
        return boletins;
    }
    
    public void setBoletins(Set boletins) {
        this.boletins = boletins;
    }
    
    public Set getPenalidades() {
        return penalidades;
    }
    
    public void setPenalidades(Set penalidades) {
        this.penalidades = penalidades;
    }
    
    public Set getExcluidos() {
        return excluidos;
    }
    
    public void setExcluidos(Set excluidos) {
        this.excluidos = excluidos;
    }
    
}
