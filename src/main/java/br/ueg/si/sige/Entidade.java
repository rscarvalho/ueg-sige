package br.ueg.si.sige;

import java.io.Serializable;

/**
 *
 * <p>Description: Value Object para manipulação de usuários</p>
 *
 * @author Rodolfo S. Carvalho
 * @version 1.0
 */
public class Entidade implements Serializable {
    private int codigo;
    private String nome;
    private String telefone;
    private String endereco;
    private int salasDeAula;
    private int anoLetivo;

    public String getNome() {
        return nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSalasDeAula(int salasDeAula) {
        this.salasDeAula = salasDeAula;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getSalasDeAula() {
        return salasDeAula;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getTelefoneFormatado() {
        String retorno;
        if (getTelefone().length() == 10) {
            retorno = "(" + getTelefone().substring(0, 2) + ") " +
                      getTelefone().substring(2, 6) + " - " + getTelefone().substring(6);
        } else {
            retorno = "(" + getTelefone().substring(0, 2) + ") " +
                      getTelefone().substring(2, 5) + " - " + getTelefone().substring(5);
        }
        return retorno;
    }

    public String toString() {
        return getNome();
    }

    public int getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }
}
