package edu.missouri.cf.pdcapp.model;



public class ListBean {
	
	private String id;
	private OracleString systemValue;
	private OracleString value;
	private boolean isDefault;
	private OracleString description;
	
	public ListBean() {
	}
	
	public ListBean(String id, OracleString systemValue, OracleString value, boolean isDefault, OracleString description) {
		this.id = id;
		this.systemValue = systemValue;
		this.value = value;
		this.isDefault = isDefault;
		this.description = description;
	}

	public OracleString getSystemValue() {
		return systemValue;
	}

	public void setSystemValue(OracleString systemValue) {
		this.systemValue = systemValue;
	}

	public OracleString getValue() {
		return value;
	}

	public void setValue(OracleString value) {
		this.value = value;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public OracleString getDescription() {
		return description;
	}

	public void setDescription(OracleString description) {
		this.description = description;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListBean other = (ListBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}