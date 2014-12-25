package org.svenehrke.javafxdemos.infra;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attribute {
	private String id;
	private String qualifier;
	private StringProperty valueProperty = new SimpleStringProperty();

	public Attribute(String value, String qualifier) {
		id = ModelStore.newId();
		this.valueProperty.setValue(value);
		this.qualifier = qualifier;
	}

	public String getId() {
		return id;
	}

	public String getQualifier() {
		return qualifier;
	}

	public String getValue() {
		return valueProperty.get();
	}
	public void setValue(String value) {
		valueProperty.setValue(value);
	}

	public StringProperty getValueProperty() {
		return valueProperty;
	}

	public void populateFromAttribute(Attribute other, boolean usingQualifier) {
		if (usingQualifier) {
			qualifier = other.qualifier;
		}
		setValue(other.getValue());
	}

	@Override
	public String toString() {
		return "Attribute{" +
			"sysid=" + System.identityHashCode(this) + '\'' +
			", id='" + id + '\'' +
			", qualifier='" + qualifier + '\'' +
			", valueProperty=" + valueProperty.get() +
			'}';
	}
}