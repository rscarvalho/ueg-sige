package br.ueg.si.sige;

public class Turma implements java.io.Serializable{
    
    private int codigo;
    
    private String literal;
    
    private int ano;
    
    private Serie serie;
    
    private Entidade entidade;
    
    private java.util.Set matriculas;
    
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
    
    public java.util.Set getMatriculas() {
        return matriculas;
    }
    
    public void setMatriculas(java.util.Set matriculas) {
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

