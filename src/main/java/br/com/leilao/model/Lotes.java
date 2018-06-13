package br.com.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lotes implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 983437341743703305L;

	private Integer id;
	
	private Integer idLoteOrigem;
	
	private String preposto;
	
	private String numero;
	
	private String criador;
	
	private String observacao;
	
	private VendedorBean vendedorBean;
	
	private String tipoCompra;
	
	private Date dataCompra;
	
	private Integer numeroParcela ;
	
	private Double valorLance ;
	
	private Double valorTotal;
	
	private Double valorDesconto ;
	
	private Double taxaInscricao;
	
	private Double taxaAntecipada;
	
	private Double comissaoVendedor ;
	
	private Double comissaoComprador;
	
	private List<CompradoresWrapper> listaCompradoresWrapper;
	
	private List<Animal> listaDeAnimais;
	private List<VendedorBean> listaDeVendedores;
	private List<VendedorBean> listaDeCompradores;
	private ArrayList<CompraLote>  listacompras;
	
	public Lotes() {
		listaDeAnimais = new ArrayList<Animal>();
		listaDeVendedores = new ArrayList<VendedorBean>();
		listaDeCompradores = new ArrayList<VendedorBean>();
		setListaCompradoresWrapper(new ArrayList<CompradoresWrapper>());
		listacompras = new ArrayList<CompraLote>();
		vendedorBean = new VendedorBean();
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCriador() {
		return criador;
	}

	public void setCriador(String criador) {
		this.criador = criador;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Animal> getListaDeAnimais() {
		return listaDeAnimais;
	}

	public void setListaDeAnimais(List<Animal> listaDeAnimais) {
		this.listaDeAnimais = listaDeAnimais;
	}

	public String getPreposto() {
		return preposto;
	}

	public void setPreposto(String preposto) {
		this.preposto = preposto;
	}

	public VendedorBean getVendedorBean() {
		return vendedorBean;
	}

	public void setVendedorBean(VendedorBean vendedorBean) {
		this.vendedorBean = vendedorBean;
	}

	public String getTipoCompra() {
		return tipoCompra;
	}

	public void setTipoCompra(String tipoCompra) {
		this.tipoCompra = tipoCompra;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Integer getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public Double getValorLance() {
		return valorLance;
	}

	public void setValorLance(Double valorLance) {
		this.valorLance = valorLance;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Double getTaxaInscricao() {
		return taxaInscricao;
	}

	public void setTaxaInscricao(Double taxaInscricao) {
		this.taxaInscricao = taxaInscricao;
	}

	public Double getTaxaAntecipada() {
		return taxaAntecipada;
	}

	public void setTaxaAntecipada(Double taxaAntecipada) {
		this.taxaAntecipada = taxaAntecipada;
	}

	public Double getComissaoVendedor() {
		return comissaoVendedor;
	}

	public void setComissaoVendedor(Double comissaoVendedor) {
		if(comissaoVendedor != null){
			this.comissaoVendedor = comissaoVendedor;
		   }
		
	}

	public Double getComissaoComprador() {
		
		return comissaoComprador;
	}

	public void setComissaoComprador(Double comissaoComprador) {
		
		this.comissaoComprador = comissaoComprador;
	}

	
	public List<VendedorBean> getListaDeVendedores() {
		return listaDeVendedores;
	}

	public void setListaDeVendedores(List<VendedorBean> listaDeVendedores) {
		this.listaDeVendedores = listaDeVendedores;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<CompraLote> getListacompras() {
		return listacompras;
	}

	public void setListacompras(ArrayList<CompraLote> listacompras) {
		this.listacompras = listacompras;
	}

	public List<VendedorBean> getListaDeCompradores() {
		return listaDeCompradores;
	}

	public void setListaDeCompradores(List<VendedorBean> listaDeCompradores) {
		this.listaDeCompradores = listaDeCompradores;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public List<CompradoresWrapper> getListaCompradoresWrapper() {
		return listaCompradoresWrapper;
	}


	public void setListaCompradoresWrapper(List<CompradoresWrapper> listaCompradoresWrapper) {
		this.listaCompradoresWrapper = listaCompradoresWrapper;
	}


	public Integer getIdLoteOrigem() {
		return idLoteOrigem;
	}


	public void setIdLoteOrigem(Integer idLoteOrigem) {
		this.idLoteOrigem = idLoteOrigem;
	}
	
}
