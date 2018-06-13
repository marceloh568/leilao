package br.com.leilao.model;

import java.io.Serializable;

public class RacaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private Boolean criacao;
	
	private String tipoCriacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getCriacao() {
		return criacao;
	}

	public void setCriacao(Boolean criacao) {
		this.criacao = criacao;
	}

	public String getTipoCriacao() {
		return tipoCriacao;
	}

	public void setTipoCriacao(String tipoCriacao) {
		this.tipoCriacao = tipoCriacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RacaBean other = (RacaBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
