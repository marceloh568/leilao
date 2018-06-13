package br.com.leilao.control;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.RacaBean;
import br.com.leilao.service.RacaService;

@javax.faces.bean.ManagedBean
@ViewScoped
public class RacaController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RacaBean racaBean;

	private RacaService racaService;
	
	public RacaController() {
		racaBean = new RacaBean();
		racaService = new RacaService();
	}
	
	public String salvar() throws ProjetoException {
		if(this.racaBean.getId() == null){
			if (racaService.salvarRaca(this.racaBean)) {
				return "list?faces-redirect=true&amp;sucesso=Raça cadastrada com sucesso.";		
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		
		} else {
			if (racaService.editarRaca(racaBean)) {
				return "list?faces-redirect=true&amp;sucesso=Raça editada com sucesso.";				
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		}
	}
	
	public String editar() throws ProjetoException {
		racaService.editarRaca(racaBean);
		return "list?faces-redirect=true&amp;sucesso=Raça editada com sucesso.";
	}
	
	public String deletar() throws ProjetoException {
		if (racaService.excluirRaca(racaBean)) {
			return "list?faces-redirect=true&amp;sucesso=Raça deletada com sucesso.";
		} else {
			return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
		}
	}
	
	public String getEditarRaca() throws ProjetoException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
		if(params.get("id") != null) {
			Integer id = Integer.parseInt(params.get("id"));
			this.racaBean = racaService.findOne(id);
		}
		return "form";
	}
	
	public String redirectEdit() {
		return "form?faces-redirect=true&amp;id=" + this.racaBean.getId();
	}
	
	public List<RacaBean> findAll() throws ProjetoException {
		return racaService.findAll();
	}

	public RacaBean getRacaBean() {
		return racaBean;
	}

	public void setRacaBean(RacaBean racaBean) {
		this.racaBean = racaBean;
	}
	
}
