package br.com.leilao.model;

import java.io.Serializable;
import java.util.List;

public class CompradoresWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VendedorBean comprador;
	
	private Double valorLance;
	
	private Double valorDesconto;
	
	private Integer numeroParcelas;

	private List<ParcelasComprador> listParcelasComprador;
	
	private Boolean editavel  = false;
	
	private VendedorBean vendedor;
	
	public VendedorBean getComprador() {
		return comprador;
	}

	public void setComprador(VendedorBean comprador) {
		this.comprador = comprador;
	}

	public Double getValorLance() {
		return valorLance;
	}

	public void setValorLance(Double valorLance) {
		this.valorLance = valorLance;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public List<ParcelasComprador> getListParcelasComprador() {
		return listParcelasComprador;
	}

	public void setListParcelasComprador(List<ParcelasComprador> listParcelasComprador) {
		this.listParcelasComprador = listParcelasComprador;
	}

	public Boolean getEditavel() {
		return editavel;
	}

	public void setEditavel(Boolean editavel) {
		this.editavel = editavel;
	}

	public VendedorBean getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorBean vendedor) {
		this.vendedor = vendedor;
	}
	
}
