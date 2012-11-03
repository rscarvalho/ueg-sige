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
 * @author Rodolfo S. Carvalho e Silvia F. Assis
 * @version 1.0
 */
public class Usuario implements Serializable{
    private int codigo;
    private String nomeCompleto;
    private Entidade entidade;
    private String login;
    private String senha;
    private Permissao[] permissoes;
    private String CPF;
    private Usuario operador;
    private boolean ativo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setPermissoes(Permissao[] permissoes) {
        this.permissoes = permissoes;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setOperador(Usuario operador) {
        this.operador = operador;
    }

    public void setCPF(String CPF) {
        String novoCPF;
        if(CPF.indexOf(".") != -1){
            novoCPF = CPF.substring(0,3)+CPF.substring(4,7)+CPF.substring(8,11)+CPF.substring(12,14);
        }else{
            novoCPF = CPF;
        }
        this.CPF = novoCPF;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Permissao[] getPermissoes() {
        return permissoes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Usuario getOperador() {
        return operador;
    }


    public String getCPF() {
        return CPF;
    }

    public String getCPFFormatado() {
        return this.CPF.substring(0, 3) + "." + this.CPF.substring(3, 6) +
                "." + this.CPF.substring(6, 9) + "-" +
                this.CPF.substring(9);
    }

    public boolean isPermitted(Permissao permissao){
        int codigo = permissao.getCodigo();
        for (int i = 0; i < permissoes.length; i++) {
            if(permissoes[i].getCodigo() == codigo) return true;
        }
        return false;
    }

}
