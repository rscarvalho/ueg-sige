package br.ueg.si.sige;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author silvinha
 */
public class HistoricoEscolar implements Serializable{
	private static final long serialVersionUID = -5707744887828894446L;
	private Aluno aluno;
    private Set<Boletim> boletins;
    private Set<Penalidade> penalidades;
    private Set<ItemHistorico> excluidos;
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
        for(Iterator<Penalidade> iter = penalidades.iterator();iter.hasNext();){
            if(((Penalidade)iter.next()).getCodigo()==penalidade.getCodigo()){
                iter.remove();
                break;
            }
        }
    }
    
    public Penalidade buscaPenalidadePorCodigo(int codigo){
        for(Iterator<Penalidade> iter = penalidades.iterator();iter.hasNext();){
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
    
    public Set<Boletim> getBoletins() {
        return boletins;
    }
    
    public void setBoletins(Set<Boletim> boletins) {
        this.boletins = boletins;
    }
    
    public Set<Penalidade> getPenalidades() {
        return penalidades;
    }
    
    public void setPenalidades(Set<Penalidade> penalidades) {
        this.penalidades = penalidades;
    }
    
    public Set<ItemHistorico> getExcluidos() {
        return excluidos;
    }
    
    public void setExcluidos(Set<ItemHistorico> excluidos) {
        this.excluidos = excluidos;
    }
    
}
