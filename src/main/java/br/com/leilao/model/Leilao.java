package br.com.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Leilao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private String endereco;
	
	private String bairro;
	
	private String cidade;
	
	private String uf;
	
	private String leiloeiro;
	
	private String gerente;
	
	private Boolean ativo;
	
	private Boolean fechado;
	
	private Date data;
	
	private Boolean utilizaComissaoVendedor;
	
	private Boolean utilizaTaxaInscricao;
	
	private Double comissaoVendedor = 0.0;
	
	private Double comissaoComprador = 0.0;
	
	private Double totalVendido = 0.0;
	
	private Double totalComissao = 0.0;
	
	private Double totalTaxaInscricao = 0.0;
	
	private Integer numeroParcela = 0;
	
	private List<RacaBean> listaDeRacas;
	
	private List<AgruparParcelas> agruparParcelas;

	private List<Lotes> lotes;
	
	private ImagemLeilao imagem;
	
	public Leilao() {
		listaDeRacas = new ArrayList<RacaBean>();
		agruparParcelas = new ArrayList<AgruparParcelas>();
		setLotes(new ArrayList<Lotes>());
	}
	
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getLeiloeiro() {
		return leiloeiro;
	}

	public void setLeiloeiro(String leiloeiro) {
		this.leiloeiro = leiloeiro;
	}

	public String getGerente() {
		return gerente;
	}

	public void setGerente(String gerente) {
		this.gerente = gerente;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getFechado() {
		return fechado;
	}

	public void setFechado(Boolean fechado) {
		this.fechado = fechado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Boolean getUtilizaComissaoVendedor() {
		return utilizaComissaoVendedor;
	}

	public void setUtilizaComissaoVendedor(Boolean utilizaComissaoVendedor) {
		this.utilizaComissaoVendedor = utilizaComissaoVendedor;
	}

	public Boolean getUtilizaTaxaInscricao() {
		return utilizaTaxaInscricao;
	}

	public void setUtilizaTaxaInscricao(Boolean utilizaTaxaInscricao) {
		this.utilizaTaxaInscricao = utilizaTaxaInscricao;
	}

	public Double getComissaoVendedor() {
		return comissaoVendedor;
	}

	public void setComissaoVendedor(Double comissaoVendedor) {
		this.comissaoVendedor = comissaoVendedor;
	}

	public Double getComissaoComprador() {
		return comissaoComprador;
	}

	public void setComissaoComprador(Double comissaoComprador) {
		this.comissaoComprador = comissaoComprador;
	}

	public Double getTotalVendido() {
		return totalVendido;
	}

	public void setTotalVendido(Double totalVendido) {
		this.totalVendido = totalVendido;
	}

	public Double getTotalComissao() {
		return totalComissao;
	}

	public void setTotalComissao(Double totalComissao) {
		this.totalComissao = totalComissao;
	}

	public Double getTotalTaxaInscricao() {
		return totalTaxaInscricao;
	}

	public void setTotalTaxaInscricao(Double totalTaxaInscricao) {
		this.totalTaxaInscricao = totalTaxaInscricao;
	}

	public Integer getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}
	
	public List<RacaBean> getListaDeRacas() {
		return listaDeRacas;
	}

	public void setListaDeRacas(List<RacaBean> listaDeRacas) {
		this.listaDeRacas = listaDeRacas;
	}

	public List<AgruparParcelas> getAgruparParcelas() {
		return agruparParcelas;
	}

	public void setAgruparParcelas(List<AgruparParcelas> agruparParcelas) {
		this.agruparParcelas = agruparParcelas;
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
		Leilao other = (Leilao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ImagemLeilao getImagem() {
		return imagem;
	}

	public void setImagem(ImagemLeilao imagem) {
		this.imagem = imagem;
	}

	public List<Lotes> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lotes> lotes) {
		this.lotes = lotes;
	}
}
