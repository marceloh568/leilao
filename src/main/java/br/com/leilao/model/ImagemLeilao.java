package br.com.leilao.model;

import java.io.Serializable;

public class ImagemLeilao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private byte[] imagem;
	
	private Leilao leilao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}
	
}
