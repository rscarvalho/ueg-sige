package br.ueg.si.sige;

import java.io.Serializable;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Modulo implements Serializable{
	private static final long serialVersionUID = -201048368438200226L;
	private int codigo;
    private Modulo moduloPai;
    private String descricao;

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setModuloPai(Modulo moduloPai) {
        this.moduloPai = moduloPai;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public Modulo getModuloPai() {
        return moduloPai;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     * @todo Implement this java.lang.Object method
     */
    public String toString() {
        return descricao;
    }
}

