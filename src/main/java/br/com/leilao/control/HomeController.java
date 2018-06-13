package br.com.leilao.control;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.AgruparParcelas;
import br.com.leilao.model.ImagemLeilao;
import br.com.leilao.model.Leilao;
import br.com.leilao.service.LeilaoService;

@ManagedBean
@ViewScoped
public class HomeController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Leilao homeLeilaoBean;

	private LeilaoService leilaoService;
	
	private AgruparParcelas agruparParcelas;
	
	private String valorMenuLateral;
	
	public HomeController() {
		homeLeilaoBean = new Leilao();
		leilaoService = new LeilaoService();
		agruparParcelas = new AgruparParcelas();
	}
	
	public String salvarHomeLeilao() throws ProjetoException {
		if (leilaoService.editarLeilao(this.homeLeilaoBean)) {
			return "dados?faces-redirect=true&amp;sucesso=Leilão editado com sucesso.&amp;dados=true";
		} else {
			return "dados?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
		}
	}
	
	public void adicionarParcelaLeilao() {
		this.homeLeilaoBean.getAgruparParcelas().add(this.agruparParcelas);
		this.agruparParcelas = new AgruparParcelas();
	}
	
	public void removerParcelaLeilao() {
		this.homeLeilaoBean.getAgruparParcelas().remove(this.agruparParcelas);
	}
	
	public void uploadImagemLeilao(FileUploadEvent event) {
		ImagemLeilao imagemLeilao = new ImagemLeilao();
		imagemLeilao.setImagem(event.getFile().getContents());
		this.homeLeilaoBean.setImagem(imagemLeilao);
	}
	
	public void selectLeilao(Leilao leilao) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("leilao", leilao);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../home/dados.faces?dados=true");
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
	
	public void selectMenuLeilao(String value){
		if (value.equals("dados")) {
			valorMenuLateral = "dados";
		}
		
		if (value.equals("lotes")) {
			valorMenuLateral = "lotes";
		}
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

	public String getValorMenuLateral() {
		return valorMenuLateral;
	}

	public void setValorMenuLateral(String valorMenuLateral) {
		this.valorMenuLateral = valorMenuLateral;
	}

}
