/**
 * 
 */
package edu.missouri.cf.pdcapp.ui.converter;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Notification;

import edu.missouri.cf.pdcapp.model.OracleCurrency;

/**
 * @author graumannc
 * 
 */
@SuppressWarnings("serial")
public class OracleCurrencyToStringConverter implements
		Converter<String, java.math.BigDecimal> {
	
	
	private static OracleCurrencyToStringConverter converter = new OracleCurrencyToStringConverter();
	
	public static OracleCurrencyToStringConverter get() {
		return converter;
	}

	@Override
	public java.math.BigDecimal convertToModel(String value,
			Class<? extends java.math.BigDecimal> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		
		try {
			
			if(value==null || "".equals(value.trim())) {
				return new OracleCurrency("0");
			} else { 
				return new OracleCurrency(value.trim());
			} 
			
		} catch (NumberFormatException nfe) {
			
			Notification.show("Currency value is incorrect");
			throw new com.vaadin.data.util.converter.Converter.ConversionException(
					"Currency value is incorrect - " + (nfe.getMessage()!=null ? nfe.getMessage() : ""));
			
		}
	}

	@Override
	public String convertToPresentation(java.math.BigDecimal value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		
		if (null != value) {
			return Formatter.getCurrencyFormat().format(value.doubleValue());
		} else {
			return "";
		}
	}

	@Override
	public Class<java.math.BigDecimal> getModelType() {
		return java.math.BigDecimal.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
