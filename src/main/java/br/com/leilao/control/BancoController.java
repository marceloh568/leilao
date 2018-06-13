package br.com.leilao.control;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.Banco;
import br.com.leilao.service.BancoService;

@javax.faces.bean.ManagedBean
@ViewScoped
public class BancoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Banco bancoBean;
	
	private BancoService bancoService;
	
	public BancoController(){
		bancoBean = new Banco();
		bancoService = new BancoService();
	}
	
	public String salvar() throws ProjetoException{
		if(this.bancoBean.getId() == null){
			if (bancoService.salvarBanco(bancoBean)) {
				return "list?faces-redirect=true&amp;sucesso=Banco cadastrado com sucesso.";
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		} else {
			if (bancoService.editarBanco(bancoBean)) {
				return "list?faces-redirect=true&amp;sucesso=Banco editado com sucesso.";
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		}
	}

	public String editar() throws ProjetoException{
		if (bancoService.editarBanco(bancoBean)) {
			return "list?faces-redirect=true&amp;sucesso=Banco editado com sucesso.";
		} else {
			return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
		}
	}
	
	public String deletar() throws ProjetoException{
		if (bancoService.excluirBanco(bancoBean)) {
			return "list?faces-redirect=true&amp;sucesso=Banco deletado com sucesso.";
		} else {
			return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
		}
	}
	
	public String preViewEditBanco() throws ProjetoException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
		if(params.get("id") != null) {
			Integer id = Integer.parseInt(params.get("id"));
			this.bancoBean = bancoService.findOne(id);
		}
		return "form";
	}
	
	public String redirectEdit(){
		return "form?faces-redirect=true&amp;id=" + this.bancoBean.getId();
	}
	public List<Banco> findAll() throws ProjetoException{
		return bancoService.findAll();
	}
	
	public Banco getBancoBean() {
		return bancoBean;
	}

	public void setBancoBean(Banco bancoBean) {
		this.bancoBean = bancoBean;
	}
	
}
