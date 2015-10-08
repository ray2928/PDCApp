package edu.missouri.cf.pdcapp.ui.converter;

import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public class OracleTimestampToDateConverter implements Converter<Date, oracle.sql.TIMESTAMP> {

	@Override
	public oracle.sql.TIMESTAMP convertToModel(Date value,
			Class<? extends oracle.sql.TIMESTAMP> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return value != null ? new oracle.sql.TIMESTAMP(new java.sql.Date(value.getTime())) : null;
	}

	@Override
	public Date convertToPresentation(oracle.sql.TIMESTAMP value,
			Class<? extends Date> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		try {
			return value != null ? new java.util.Date(value.dateValue().getTime()) : null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Class<oracle.sql.TIMESTAMP> getModelType() {
		return oracle.sql.TIMESTAMP.class;
	}

	@Override
	public Class<Date> getPresentationType() {
		return java.util.Date.class;
	}

}
