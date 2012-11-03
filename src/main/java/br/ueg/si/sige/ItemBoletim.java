package br.ueg.si.sige;

/**
 *
 * @author silvinha
 */
public class ItemBoletim implements java.io.Serializable{
    
    private int codigo;
    private float nota;
    private int faltas;
    private int bimesLetivo;
    private Disciplina disciplina;
    
    
    public ItemBoletim() {
        
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.setNota(nota);
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getBimesLetivo() {
        return bimesLetivo;
    }

    public void setBimesLetivo(int bimesLetivo) {
        this.bimesLetivo = bimesLetivo;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
    
}
