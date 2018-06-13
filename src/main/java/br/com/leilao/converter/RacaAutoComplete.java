package br.com.leilao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.dao.RacaDao;
import br.com.leilao.model.RacaBean;

@FacesConverter(value = "racaAutoComplete")
public class RacaAutoComplete implements Converter {

	private RacaDao racaDao;
	
	public RacaAutoComplete() {
		racaDao = new RacaDao();
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String value) {
		RacaBean raca = null;
		try {
			raca = racaDao.findOne(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			System.out.println("erro getAsObject racaAutoComplete" + e);
		} catch (ProjetoException e) {
			System.out.println("erro getAsObject racaAutoComplete" + e);
		}
		return raca;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object value) {
		RacaBean raca = (RacaBean) value;
		return String.valueOf(raca.getId());
	}

}
