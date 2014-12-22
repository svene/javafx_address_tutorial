package org.svenehrke.javafxdemos.address;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import org.svenehrke.javafxdemos.address.model.Person;
import org.svenehrke.javafxdemos.address.model.SampleData;
import org.svenehrke.javafxdemos.infra.ImpulseListeners;

import java.util.function.Function;

public class Model {

	private SampleData sampleData;

	public IntegerProperty selectedModelIndex = new SimpleIntegerProperty(-1);

	public final Person currentPerson = newEmptyPerson();
	public final Person workPerson = newEmptyPerson();
	public final Person emptyPerson = newEmptyPerson();
	public final BooleanProperty editOkButtonClicked = new SimpleBooleanProperty();
	public final BooleanProperty newOkButtonClicked = new SimpleBooleanProperty();

	public Model() {
		sampleData = new SampleData();

		// Update 'currentPerson', e.g. when table selection changes:
		selectedModelIndex.addListener((s, o, n) -> {
				if (n.intValue() >= 0) {
					System.out.println("populated");
					Person person = getPersonData().get(n.intValue());
					currentPerson.populateFromPerson(person);
				}
			}
		);

		// Update item in 'sampleData' to which 'currentPerson' corresponds to when 'currentPerson' changes (e.g. when it is edited):
		copyPropertyOnChange(currentPerson, Person::firstNameProperty);
		copyPropertyOnChange(currentPerson, Person::lastNameProperty);
		copyPropertyOnChange(currentPerson, Person::streetProperty);
		copyPropertyOnChange(currentPerson, Person::postalCodeProperty);
		copyPropertyOnChange(currentPerson, Person::cityProperty);
		copyPropertyOnChange(currentPerson, Person::birthdayProperty);

		ImpulseListeners.bindImpulseListener(editOkButtonClicked, () -> {
			currentPerson.populateFromPerson(workPerson);
		});
		ImpulseListeners.bindImpulseListener(newOkButtonClicked, () -> {
			getPersonData().add(workPerson);
			selectedModelIndex.setValue(getPersonData().size() - 1);
		});

	}

	private <T> void copyPropertyOnChange(Person sourcePerson, Function<Person, Property<T>> pf) {
		pf.apply(sourcePerson).addListener((s, o, n) -> {
			int idx = selectedModelIndex.get();
			pf.apply(getPersonData().get(idx)).setValue(n);
		});
	}

	public ObservableList<Person> getPersonData() {
		return sampleData.getPersonData();
	}

	public Person getCurrentPerson() {
		return currentPerson;
	}

	public Person getWorkPerson() {
		return workPerson;
	}

	private Person newEmptyPerson() {
		Person result = new Person("", "");
		result.setCity("");
		result.setPostalCode(0);
		result.setStreet("");
		return result;
	}

}
