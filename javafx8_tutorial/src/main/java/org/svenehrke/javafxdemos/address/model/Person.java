
package org.svenehrke.javafxdemos.address.model;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlAttribute;
import java.time.LocalDate;

public class Person {

	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty street;
	private IntegerProperty postalCode;
	private StringProperty city;
	private ObjectProperty<LocalDate> birthday;

	public Person() {
		this(null, null);
	}
	public Person(String firstNameProperty, String lastNameProperty) {
		this.firstName = new SimpleStringProperty(firstNameProperty);
		this.lastName = new SimpleStringProperty(lastNameProperty);

		// Some initial dummy data, just for convenient testing.
		this.street = new SimpleStringProperty("some street");
		this.postalCode = new SimpleIntegerProperty(1234);
		this.city = new SimpleStringProperty("some city");
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
	}

	public void populateFromPerson(Person other) {
		this.firstName.setValue(other.firstName.getValue());
		this.lastName.setValue(other.lastName.getValue());
		this.street.setValue(other.street.getValue());
		this.postalCode.setValue(other.postalCode.getValue());
		this.city.setValue(other.city.getValue());
		this.birthday.setValue(other.birthday.getValue());
	}

	@XmlAttribute
	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	@XmlAttribute
	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	@XmlAttribute
	public String getStreet() {
		return street.get();
	}

	public void setStreet(String street) {
		this.street.set(street);
	}

	public StringProperty streetProperty() {
		return street;
	}

	@XmlAttribute
	public int getPostalCode() {
		return postalCode.get();
	}

	public void setPostalCode(int postalCode) {
		this.postalCode.set(postalCode);
	}

	public IntegerProperty postalCodeProperty() {
		return postalCode;
	}

	@XmlAttribute
	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {
		this.city.set(city);
	}

	public StringProperty cityProperty() {
		return city;
	}

	@XmlAttribute
	public LocalDate getBirthday() {
		return birthday.get();
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}

	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}

	@Override
	public String toString() {
		return "Person{" +
			"firstName=" + firstName.getValue() +
			", lastName=" + lastName.getValue() +
			", street=" + street.getValue() +
			", postalCode=" + postalCode.getValue() +
			", city=" + city.getValue() +
			", birthday=" + birthday.getValue() +
			'}';
	}
}
