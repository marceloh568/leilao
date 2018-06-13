package br.com.leilao.model;

import java.io.Serializable;

public class GeneologiaPai implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pai;
	
	private String avoMasculino;
	
	private String avoFeminino;
	
	private String bisavoMasculino;

	private String bisavoFeminino;

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getAvoMasculino() {
		return avoMasculino;
	}

	public void setAvoMasculino(String avoMasculino) {
		this.avoMasculino = avoMasculino;
	}

	public String getAvoFeminino() {
		return avoFeminino;
	}

	public void setAvoFeminino(String avoFeminino) {
		this.avoFeminino = avoFeminino;
	}

	public String getBisavoMasculino() {
		return bisavoMasculino;
	}

	public void setBisavoMasculino(String bisavoMasculino) {
		this.bisavoMasculino = bisavoMasculino;
	}

	public String getBisavoFeminino() {
		return bisavoFeminino;
	}

	public void setBisavoFeminino(String bisavoFeminino) {
		this.bisavoFeminino = bisavoFeminino;
	}
}
