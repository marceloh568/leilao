package br.com.leilao.model;

import java.io.Serializable;

public class UsuarioBean implements Serializable {

    // private static final long serialVersionUID = 1L;
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer codfunc;
    private String cpf;
    private String nome;
    private String login;
    private String senha;
    private boolean adm;
    
    private Integer idPerfil;
    private String descPerfil;

    public UsuarioBean() {
    }

    public Integer getCodfunc() {
        return codfunc;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setCodfunc(Integer codfunc) {
        this.codfunc = codfunc;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }


    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getDescPerfil() {
        return descPerfil;
    }

    public void setDescPerfil(String descPerfil) {
        this.descPerfil = descPerfil;
    }
}