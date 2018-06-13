package br.com.leilao.acl.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@FacesConverter("com.magnani.test.converter.MoneyConverter")
public class MoneyConverter implements Converter {
 
 final private Locale locale = new Locale("pt", "BR");
 final private DecimalFormat decimalFormat = new DecimalFormat("##0,00", new DecimalFormatSymbols(locale));
 
 public BigDecimal getAsObject(FacesContext fc, UIComponent component, String value) {
 
 try {
 
   decimalFormat.setParseBigDecimal(true);
 
   return (BigDecimal) decimalFormat.parse(value);
 } catch (ParseException | java.text.ParseException e) {
  throw new ConverterException("Error", e);
 }
 
}
 
public String getAsString(FacesContext fc, UIComponent component, Object value) {
  DecimalFormat df = new DecimalFormat("###,###,##0.00");
  return df.format(value);
 }
}