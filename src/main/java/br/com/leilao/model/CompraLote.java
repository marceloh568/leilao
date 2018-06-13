package br.com.leilao.model;

import java.io.Serializable;
import java.util.Date;

public class CompraLote implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private VendedorBean vendedor;
	
	private String tipoCompra;
	
	private Date dataDaCompra;
	
	private Double valorDoLance = 0.0;
	
	private Integer numeroDeParcelas = 0;
	
	private Double valorTotal = 0.0;
	
	private Double valorDesconto = 0.0;
	
	private Double taxaInscricao = 0.0;
	
	private Double taxaAntecipada = 0.0;
	
	private Double comissaoVendedor = 0.0;
	
	private Double comissaoComprador = 0.0;
	
	
	public CompraLote() {
		vendedor = new VendedorBean();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public VendedorBean getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorBean vendedor) {
		this.vendedor = vendedor;
	}

	public String getTipoCompra() {
		return tipoCompra;
	}

	public void setTipoCompra(String tipoCompra) {
		this.tipoCompra = tipoCompra;
	}

	public Date getDataDaCompra() {
		return dataDaCompra;
	}

	public void setDataDaCompra(Date dataDaCompra) {
		this.dataDaCompra = dataDaCompra;
	}

	public Double getValorDoLance() {
		return valorDoLance;
	}

	public void setValorDoLance(Double valorDoLance) {
		this.valorDoLance = valorDoLance;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
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
		this.comissaoVendedor = comissaoVendedor;
	}

	public Double getComissaoComprador() {
		return comissaoComprador;
	}

	public void setComissaoComprador(Double comissaoComprador) {
		this.comissaoComprador = comissaoComprador;
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
		CompraLote other = (CompraLote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
