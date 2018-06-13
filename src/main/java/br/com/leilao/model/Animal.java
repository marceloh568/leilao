package br.com.leilao.model;

import java.io.Serializable;
import java.util.Date;

public class Animal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private String pelagem;
	
	private Date dataNascimento;
	
	private String numeroDeRegistro;
	
	private String numeroDeControle;
	
	private String sexo;
	
	private String criador;
	
	private RacaBean racaBean;
	
	private GeneologiaPai geneologiaPai;
	
	private GeneologiaMae geneologiaMae;
	
	public Animal() {
		racaBean = new RacaBean();
		geneologiaPai = new GeneologiaPai();
		geneologiaMae = new GeneologiaMae();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPelagem() {
		return pelagem;
	}

	public void setPelagem(String pelagem) {
		this.pelagem = pelagem;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNumeroDeRegistro() {
		return numeroDeRegistro;
	}

	public void setNumeroDeRegistro(String numeroDeRegistro) {
		this.numeroDeRegistro = numeroDeRegistro;
	}

	public String getNumeroDeControle() {
		return numeroDeControle;
	}

	public void setNumeroDeControle(String numeroDeControle) {
		this.numeroDeControle = numeroDeControle;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCriador() {
		return criador;
	}

	public void setCriador(String criador) {
		this.criador = criador;
	}

	public RacaBean getRacaBean() {
		return racaBean;
	}

	public void setRacaBean(RacaBean racaBean) {
		this.racaBean = racaBean;
	}

	public GeneologiaPai getGeneologiaPai() {
		return geneologiaPai;
	}

	public void setGeneologiaPai(GeneologiaPai geneologiaPai) {
		this.geneologiaPai = geneologiaPai;
	}

	public GeneologiaMae getGeneologiaMae() {
		return geneologiaMae;
	}

	public void setGeneologiaMae(GeneologiaMae geneologiaMae) {
		this.geneologiaMae = geneologiaMae;
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
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
