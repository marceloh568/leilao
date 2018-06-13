package br.com.leilao.control;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.model.Animal;
import br.com.leilao.service.AnimalService;

@ManagedBean
@ViewScoped
public class AnimalController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Animal animalBean;
	
	private AnimalService animalService;
	
	public AnimalController() {
		animalBean = new Animal();
		animalService = new AnimalService();
	}
	
	public String salvarAnimal() throws ProjetoException {
		if(this.animalBean.getId() == null){
			if (animalService.salvarAnimal(this.animalBean)) {
				return "list?faces-redirect=true&amp;sucesso=Animal cadastrado com sucesso.";		
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		} else {
			if (animalService.editarAnimal(this.animalBean)) {
				return "list?faces-redirect=true&amp;sucesso=Animal editado com sucesso.";				
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operaçãocontate o administrador do sistema.";
			}
		}
	}
	
	public String excluirAnimal() throws ProjetoException {
		if (animalService.excluirAnimal(this.animalBean)) {
			return "list?faces-redirect=true&amp;sucesso=Animal deletado com sucesso.";
		} else {
			return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
		}
	}
	
	public String getEditAnimal() throws ProjetoException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
		if(params.get("id") != null) {
			Integer id = Integer.parseInt(params.get("id"));
			this.animalBean = animalService.findOne(id);
		}
		return "form";
	}
	
	public String redirectEdit() {
		return "form?faces-redirect=true&amp;id=" + this.animalBean.getId();
	}
	
	public List<Animal> findAll() throws ProjetoException {
		return animalService.findAll();
	}

	public Animal getAnimalBean() {
		return animalBean;
	}

	public void setAnimalBean(Animal animalBean) {
		this.animalBean = animalBean;
	}
}
