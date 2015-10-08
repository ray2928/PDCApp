package edu.missouri.cf.pdcapp.ui.converter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public class OracleTimestampToStringConverter implements Converter<String, oracle.sql.TIMESTAMP> {

	@Override
	public oracle.sql.TIMESTAMP convertToModel(String value,
			Class<? extends oracle.sql.TIMESTAMP> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		try {
			return value != null ? new oracle.sql.TIMESTAMP(java.sql.Date.valueOf(value)) : null;
		} catch (IllegalArgumentException e) {
			throw new com.vaadin.data.util.converter.Converter.ConversionException(e.getMessage());
		}
	}

	@Override
	public String convertToPresentation(oracle.sql.TIMESTAMP value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
			try {
//				System.out.println(Formatter.TIMESTAMP.format(value.dateValue().getTime()));
				return value != null ? Formatter.TIMESTAMP.format(value.dateValue().getTime()) : null;
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
	public Class<String> getPresentationType() {
		return String.class;
	}

}
