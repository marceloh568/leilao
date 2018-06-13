package br.com.leilao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.AnimalDao;
import br.com.leilao.model.Animal;

@FacesConverter(value = "animalAutoComplete")
public class AnimalAutoComplete implements Converter {

	private AnimalDao animalDao;
	
	public AnimalAutoComplete() {
		animalDao = new AnimalDao();
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String value) {
		Animal animal = null;
		try {
			animal = animalDao.findOne(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			System.out.println("erro getAsObject animalAutoComplete" + e);
		} catch (ProjetoException e) {
			System.out.println("erro getAsObject animalAutoComplete" + e);
		}
		return animal;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object value) {
		Animal animal = (Animal) value;
		return String.valueOf(animal.getId());
	}

}
