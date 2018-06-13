package br.com.leilao.control;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.Banco;
import br.com.leilao.model.ContaCorrente;
import br.com.leilao.model.RacaBean;
import br.com.leilao.model.VendedorBean;
import br.com.leilao.service.BancoService;
import br.com.leilao.service.RacaService;
import br.com.leilao.service.VendededorService;

@ManagedBean
@ViewScoped
public class VendedorController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VendedorBean vendedorBean;
	
	private VendededorService vendedorService;

	private RacaBean racaBean;
	
	private RacaService racaService;
	
	private ContaCorrente contaCorrente;
	
	private BancoService bancoService;
	
	public VendedorController() {
		vendedorBean = new VendedorBean();
		racaBean = new RacaBean();
		vendedorService = new VendededorService();
		racaService = new RacaService();
		contaCorrente = new ContaCorrente();
		bancoService = new BancoService();
	}
	
	public String salvar() throws ProjetoException {
		if (this.vendedorBean.getId() == null) {
			if (vendedorService.salvarVendedor(this.vendedorBean)) {
				return "list?faces-redirect=true&amp;sucesso=Comprador/Vendedor cadastrado com sucesso.";
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		} else {
			if (vendedorService.editarVendedor(vendedorBean)) {
				return "list?faces-redirect=true&amp;sucesso=Comprador/Vendedor editado com sucesso.";
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		}
	}
	
	public String excluirVendedor() throws ProjetoException {
		if (vendedorService.excluirVendedor(this.vendedorBean.getId())) {
			return "list?faces-redirect=true&amp;sucesso=Comprador/Vendedor excluido com sucesso.";
		} else {
			return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
		}
	}

	public void adicionarAnimaisQueCria() throws ProjetoException {
		RacaBean raca = racaService.findOne(this.racaBean.getId());
		vendedorBean.getAnimaisQueCria().add(raca);
	}

	public void removeAnimaisQueCria() throws ProjetoException {
		RacaBean raca = racaService.findOne(this.racaBean.getId());
		vendedorBean.getAnimaisQueCria().remove(raca);
	}
	
	public void adicionarContaCorrente() throws ProjetoException {
		Banco banco = bancoService.findOne(this.contaCorrente.getBanco().getId());
		this.contaCorrente.setBanco(banco);
		vendedorBean.getContasCorrentes().add(this.contaCorrente);
	}
	
	public String redirectEdit() {
		return "form?faces-redirect=true&amp;id=" + this.vendedorBean.getId();
	}
	
	public String getEditVendedor() throws ProjetoException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
		if(params.get("id") != null) {
			Integer id = Integer.parseInt(params.get("id"));
			this.vendedorBean = vendedorService.findOne(id);
		}
		return "form";
	}

	public List<VendedorBean> findAll() throws ProjetoException {
		return vendedorService.findAll();
	}
	
	public VendedorBean getVendedorBean() {
		return vendedorBean;
	}

	public void setVendedorBean(VendedorBean vendedorBean) {
		this.vendedorBean = vendedorBean;
	}

	public RacaBean getRacaBean() {
		return racaBean;
	}

	public void setRacaBean(RacaBean racaBean) {
		this.racaBean = racaBean;
	}

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

}
