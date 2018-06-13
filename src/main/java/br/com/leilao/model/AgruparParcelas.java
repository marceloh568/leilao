package br.com.leilao.model;

import java.io.Serializable;

public class AgruparParcelas implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer agruparParcela;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgruparParcela() {
		return agruparParcela;
	}

	public void setAgruparParcela(Integer agruparParcela) {
		this.agruparParcela = agruparParcela;
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
		AgruparParcelas other = (AgruparParcelas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
