package edu.missouri.cf.pdcapp.ui.converter;

import java.util.Locale;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;

import edu.missouri.cf.pdcapp.model.OracleString;

public class SingleSelectConverter <T> implements Converter<Object, OracleString>{
	private final AbstractSelect select;
	
	public SingleSelectConverter(AbstractSelect select) {
		this.select = select;
	}
	
	private BeanContainer<String, T> getContainer() {
		return (BeanContainer<String, T>) select.getContainerDataSource();
	}

	
	@Override
	public OracleString convertToModel(Object value,
			Class<? extends OracleString> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		if (value!=null && value != select.getNullSelectionItemId() && select.size() != 0) {
			try {
				
				if (getContainer() != null && getContainer().getItem(value) != null
						&& getContainer().getItem(value).getBean() != null) {
					return new OracleString(getContainer().getItem(value).getBean().toString());
				} else {
					return null;
				}
				
			} catch (NullPointerException npe) {
				return null;
			}
		}
		return null;
		
	}

	@Override
	public Object convertToPresentation(OracleString value,
			Class<? extends Object> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		if (value != null) {
			return value.toString();
		}
		return select.getNullSelectionItemId();
	}

	@Override
	public Class<OracleString> getModelType() {
		// TODO Auto-generated method stub
		return OracleString.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		// TODO Auto-generated method stub
		return Object.class;
	}

}
