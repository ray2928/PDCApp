package edu.missouri.cf.pdcapp.ui.component;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.ComboBox;

import edu.missouri.cf.pdcapp.model.ListBean;
import edu.missouri.cf.pdcapp.model.ListItems;
import edu.missouri.cf.pdcapp.ui.converter.SingleSelectConverter;

public class StandardComboBox extends ComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8495731588370667604L;
	private String listName = null;
	private String campusId = null;

	public StandardComboBox() {
		super();
		setConverter(new SingleSelectConverter<Object>(this));
		setImmediate(true);
		setScrollToSelectedItem(true);
	}

	public StandardComboBox(String listName, String caption) {
		super(caption);
		this.setListName(listName);
		setConverter(new SingleSelectConverter<Object>(this));
		setImmediate(true);
		setScrollToSelectedItem(true);
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getCampusId() {
		return campusId;
	}

	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}

	public void refreshDataCollection() {

		final BeanContainer<String, ListBean> bc = ListItems.getBeanContainer(listName, campusId);

		if (bc.size() > 0) {

			if (isReadOnly()) {

				setReadOnly(false);
				setContainerDataSource(bc);
				setItemCaptionPropertyId("description");
				setReadOnly(true);

			} else {

				setContainerDataSource(bc);
				setItemCaptionPropertyId("description");

			}

			if (bc.size() < 15) {
				setPageLength(0);
			}

		}

	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
	}

}