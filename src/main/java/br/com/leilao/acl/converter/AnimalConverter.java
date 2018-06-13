package br.com.leilao.acl.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.leilao.dao.AnimalDao;
import br.com.leilao.model.Animal;




@FacesConverter(value = "conAnimal")
public class AnimalConverter implements Converter {
	   AnimalDao icdao = new AnimalDao();
	    public Object getAsObject(FacesContext contet, UIComponent component, String value) {
	        if(value==null || value.equals(""))
	            return null;
	        
	        try{
	        	System.out.println("value"+value);
	        	int id = 0;
	        	try {
	        		id = Integer.parseInt(value);	
				} catch (Exception e) {
					// TODO: handle exception
				}
	        		
	            
	        	
	            
	            return icdao.findOne(id);
	        }catch (Exception e) {
	            e.printStackTrace();
	            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Marca no válida", ""));
	        }
	    }

	    public String getAsString(FacesContext contet, UIComponent component, Object value) {
	        if(value==null || value.equals(""))
	            return null;
	        return String.valueOf(((Animal)value).getId());
	    }
	}
