package br.com.leilao.control;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.AgruparParcelas;
import br.com.leilao.model.ImagemLeilao;
import br.com.leilao.model.Leilao;
import br.com.leilao.model.RacaBean;
import br.com.leilao.service.ImageService;
import br.com.leilao.service.LeilaoService;

@ManagedBean
@ViewScoped
public class LeilaoController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Leilao leilaoBean;
	
	private LeilaoService leilaoService;
	
	private RacaBean racaBean;
	
	private Leilao homeLeilaoBean;
	
	private AgruparParcelas agruparParcelas;
	
	public LeilaoController () {
		leilaoBean = new Leilao();
		homeLeilaoBean = new Leilao();
		racaBean = new RacaBean();
		agruparParcelas = new AgruparParcelas();
		leilaoService = new LeilaoService();
		new ImageService();
	}
	
	public String salvarLeilao() throws ProjetoException {
		if(this.leilaoBean.getId() == null){
			if (leilaoService.salvarLeilao(this.leilaoBean)) {
				return "list?faces-redirect=true&amp;sucesso=Leilão cadastrado com sucesso.";		
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		} else {
			if (leilaoService.editarLeilao(this.leilaoBean)) {
				return "list?faces-redirect=true&amp;sucesso=Leilão editado com sucesso.";		
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		}
	}
	
	public String deletarLeilao() throws ProjetoException {
		if (leilaoService.excluirLeilao(this.leilaoBean)) {
			return "list?faces-redirect=true&amp;sucesso=Leilão excluido com sucesso.";
		} else {
			return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
		}
	}
	
	public String getEditLeilao() throws ProjetoException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
		if(params.get("id") != null) {
			Integer id = Integer.parseInt(params.get("id"));
			this.leilaoBean = leilaoService.findOne(id);
		}
		return "form";
	}
	
	public void selectLeilao(Leilao leilao) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("leilao", leilao);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/leilao/pages/comum/homeLeilao.faces?dados=true");
	}
	
	public Leilao retornoLeilaoEscolhido() {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("leilao") != null) {
			Leilao leilao = (Leilao) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("leilao");
			this.homeLeilaoBean = leilao;
			return this.homeLeilaoBean;
		} else {
			return null;
		}
	}
	
	public void adicionarParcelaLeilao() {
		this.leilaoBean.getAgruparParcelas().add(this.agruparParcelas);
		this.agruparParcelas = new AgruparParcelas();
	}
	
	public void removerParcelaLeilao() {
		this.leilaoBean.getAgruparParcelas().remove(this.agruparParcelas);
	}
	
	public void adicionarRaca() {
		this.leilaoBean.getListaDeRacas().add(this.racaBean);
	}
	
	public void removeRaca() {
		this.leilaoBean.getListaDeRacas().remove(this.racaBean);
	}
	
	public String redirectEdit() {
		return "form?faces-redirect=true&amp;id=" + this.leilaoBean.getId();
	}
	
	public void uploadImagemLeilao(FileUploadEvent event) {
		ImagemLeilao imagemLeilao = new ImagemLeilao();
		imagemLeilao.setImagem(event.getFile().getContents());
		this.leilaoBean.setImagem(imagemLeilao);
	}
	
	public List<Leilao> findAll() throws ProjetoException {
		return leilaoService.findAll();
	}
	
	public Leilao getLeilaoBean() {
		return leilaoBean;
	}

	public void setLeilaoBean(Leilao leilaoBean) {
		this.leilaoBean = leilaoBean;
	}

	public RacaBean getRacaBean() {
		return racaBean;
	}

	public void setRacaBean(RacaBean racaBean) {
		this.racaBean = racaBean;
	}

	public Leilao getHomeLeilaoBean() {
		return homeLeilaoBean;
	}

	public void setHomeLeilaoBean(Leilao homeLeilaoBean) {
		this.homeLeilaoBean = homeLeilaoBean;
	}

	public AgruparParcelas getAgruparParcelas() {
		return agruparParcelas;
	}

	public void setAgruparParcelas(AgruparParcelas agruparParcelas) {
		this.agruparParcelas = agruparParcelas;
	}

}
