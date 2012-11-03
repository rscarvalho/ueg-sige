package br.ueg.si.sige;
/**
 * SQL para matricula do aluno
 * INSERT INTO matriculas values(default,gera_matricula(<cd_turma>,(SELECT sequencia from sequencia_turma where cd_turma=<cd_turma>)),<cd_aluno>,<cd_turma>);
 * UPDATE sequencia_turma SET sequencia=sequencia+1 WHERE cd_turma=<cd_turma>
 */
public class Matricula implements java.io.Serializable{
    
    private int codigo;
    
    private String numeracao;
    
    private Aluno aluno;
    
    private Turma turma;
    
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public String getNumeracao() {
        return numeracao;
    }
    
    public void setNumeracao(String numeracao) {
        this.numeracao = numeracao;
    }
    
    public Aluno getAluno() {
        return aluno;
    }
    
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    
    public Turma getTurma() {
        return turma;
    }
    
    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    
}

