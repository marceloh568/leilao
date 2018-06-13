package br.com.leilao.model;

import java.io.Serializable;

public class GeneologiaMae implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mae;
	
	private String avoMasculino;
	
	private String avoFemenino;
	
	private String bisavoMasculino;
	
	private String bisavoFemenino;

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public String getAvoMasculino() {
		return avoMasculino;
	}

	public void setAvoMasculino(String avoMasculino) {
		this.avoMasculino = avoMasculino;
	}

	public String getAvoFemenino() {
		return avoFemenino;
	}

	public void setAvoFemenino(String avoFemenino) {
		this.avoFemenino = avoFemenino;
	}

	public String getBisavoMasculino() {
		return bisavoMasculino;
	}

	public void setBisavoMasculino(String bisavoMasculino) {
		this.bisavoMasculino = bisavoMasculino;
	}

	public String getBisavoFemenino() {
		return bisavoFemenino;
	}

	public void setBisavoFemenino(String bisavoFemenino) {
		this.bisavoFemenino = bisavoFemenino;
	}
}
