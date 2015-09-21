package com.ui.converter;

import java.math.BigDecimal;
import java.util.Locale;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.converter.Converter;

public class BigDecimalToBooleanConverter implements Converter<Boolean, java.math.BigDecimal>{

	@Override
	public BigDecimal convertToModel(Boolean value,
			Class<? extends BigDecimal> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		if(value == null || !value.booleanValue()) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.ONE;
	}

	@Override
	public Boolean convertToPresentation(BigDecimal value,
			Class<? extends Boolean> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		if(value.equals(BigDecimal.ONE)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Class<BigDecimal> getModelType() {
		// TODO Auto-generated method stub
		return BigDecimal.class;
	}

	@Override
	public Class<Boolean> getPresentationType() {
		// TODO Auto-generated method stub
		return Boolean.class;
	}

}
