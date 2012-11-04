package br.ueg.si.sige;

import java.util.Set;

public class Turma implements java.io.Serializable{
	private static final long serialVersionUID = 7984561126613312885L;

	private int codigo;
    
    private String literal;
    
    private int ano;
    
    private Serie serie;
    
    private Entidade entidade;
    
    private Set<Matricula> matriculas;
    
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public String getLiteral() {
        return literal;
    }
    
    public void setLiteral(String literal) {
        this.literal = literal;
    }
    
    public int getAno() {
        return ano;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }
    
    public Serie getSerie() {
        return serie;
    }
    
    public void setSerie(Serie serie) {
        this.serie = serie;
    }
    
    public Entidade getEntidade() {
        return entidade;
    }
    
    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }
    
    public Set<Matricula> getMatriculas() {
        return matriculas;
    }
    
    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    
    public boolean addMatricula(Matricula matricula){
        return this.matriculas.add(matricula);        
    }
    
    public boolean removeMatricula(Matricula matricula){
        /**
         * TODO implement
         */
        return false;
    }
    
}

