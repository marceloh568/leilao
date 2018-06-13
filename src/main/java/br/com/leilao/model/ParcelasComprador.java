package br.com.leilao.model;

import java.io.Serializable;
import java.util.Date;

public class ParcelasComprador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numeroDaParcela;
	
	private Double valorDaParcela;
	
	private VendedorBean comprador;
	
	private Date dataVencimento;

	public Integer getNumeroDaParcela() {
		return numeroDaParcela;
	}

	public void setNumeroDaParcela(Integer numeroDaParcela) {
		this.numeroDaParcela = numeroDaParcela;
	}

	public Double getValorDaParcela() {
		return valorDaParcela;
	}

	public void setValorDaParcela(Double valorDaParcela) {
		this.valorDaParcela = valorDaParcela;
	}

	public VendedorBean getComprador() {
		return comprador;
	}

	public void setComprador(VendedorBean comprador) {
		this.comprador = comprador;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
}
