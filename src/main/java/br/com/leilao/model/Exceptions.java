package br.com.leilao.model;

import java.io.Serializable;

public class Exceptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String mensagem;
	
	private String nomeMetodo;
	
	private Integer idUsuario;

	public Exceptions() {
	}
	
	public Exceptions(String mensagem, String nomeMetodo) {
		super();
		this.mensagem = mensagem;
		this.nomeMetodo = nomeMetodo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

}
