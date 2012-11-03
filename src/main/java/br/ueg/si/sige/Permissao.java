package br.ueg.si.sige;

import java.io.Serializable;

public class Permissao implements Serializable{
    private int codigo;
    private Modulo modulo;
    private String descricao;

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCodigo() {
        return codigo;
    }
}
