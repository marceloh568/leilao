package br.com.leilao.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.leilao.dao.VendedorDao;
import br.com.leilao.model.VendedorBean;




@FacesConverter(value = "conVendedor")
public class VendedorConverter implements Converter {
	   VendedorDao icdao = new VendedorDao();
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
	            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vendedor inválido", ""));
	        }
	    }

	    public String getAsString(FacesContext contet, UIComponent component, Object value) {
	        if(value==null || value.equals(""))
	            return null;
	        return String.valueOf(((VendedorBean)value).getId());
	    }
	}
