package com.ui.converter;

import java.util.Collection;
import java.util.Locale;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.sqlcontainer.RowId;

public class StringToRowIdConverter<T> implements Converter<RowId, String> {

	Container container;

	public StringToRowIdConverter(Container container) {
		this.container = container;
//		Collection<?> itemIds = container.getItemIds();
//		System.out.println("container contents = "+itemIds.toString());
	}

	@Override
	public String convertToModel(RowId value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		if (value != null) {
			/*
			 * Get item from container get the value from the item.
			 */
			Item i = container.getItem(value);
			if (i != null && i.getItemProperty("ID")!=null && i.getItemProperty("ID").getValue()!=null) {
				return i.getItemProperty("ID").getValue().toString();
			}

		}
		return null;
	}

	@Override
	public RowId convertToPresentation(String value,
			Class<? extends RowId> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		if (value != null) {
			return new RowId(value.toString());
		}
		return null;
	}

	@Override
	public Class<String> getModelType() {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public Class<RowId> getPresentationType() {
		// TODO Auto-generated method stub
		return RowId.class;
	}

}
