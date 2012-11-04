package br.ueg.si.sige;

/**
 *
 * @author silvinha
 */
public class Penalidade implements java.io.Serializable, ItemHistorico {
	private static final long serialVersionUID = -2735080008820602201L;
	private int codigo;
    private String tipo;
    private String descricao;
    private Matricula matricula;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
        
}
