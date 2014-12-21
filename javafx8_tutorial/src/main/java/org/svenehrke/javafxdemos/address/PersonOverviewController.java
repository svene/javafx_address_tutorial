package org.svenehrke.javafxdemos.address;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import org.svenehrke.javafxdemos.address.commandhandler.DeletePersonCommandHandler;
import org.svenehrke.javafxdemos.address.commandhandler.EditPersonCommandHandler;
import org.svenehrke.javafxdemos.address.commandhandler.NewPersonCommandHandler;
import org.svenehrke.javafxdemos.address.model.Person;
import org.svenehrke.javafxdemos.infra.Mate;

public class PersonOverviewController extends AbstractPersonOverviewController {

	private Mate mate;

	private NewPersonCommandHandler newPersonCommandHandler;
	private EditPersonCommandHandler editPersonCommandHandler;
	private DeletePersonCommandHandler deletePersonCommandHandler;

	public PersonOverviewController(Mate mate) {
		this.mate = mate;
		newPersonCommandHandler = new NewPersonCommandHandler(mate.getPrimaryStage(), mate.getModel());
		editPersonCommandHandler = new EditPersonCommandHandler(mate.getPrimaryStage(), mate.getModel());
		deletePersonCommandHandler = new DeletePersonCommandHandler(mate.getModel());
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		newButton.setOnAction(event -> newPersonCommandHandler.run());
		editButton.setOnAction(event -> editPersonCommandHandler.run());
		deleteButton.setOnAction(event -> deletePersonCommandHandler.run());

		Person cp = mate.getModel().currentPerson;
		firstNameLabel.textProperty().bind(cp.firstNameProperty());
		lastNameLabel.textProperty().bind(cp.lastNameProperty());
		streetLabel.textProperty().bind(cp.streetProperty());
		postalCodeLabel.textProperty().bind(Bindings.convert(cp.postalCodeProperty()));
		cityLabel.textProperty().bind(cp.cityProperty());
		birthdayLabel.textProperty().bind(Bindings.convert(cp.birthdayProperty()));
	}

	public void postInitialize() {

		personTable.setItems(mate.getModel().getPersonData());
		personTable.getSelectionModel().selectedIndexProperty().addListener((s, o, n) -> mate.getModel().selectedModelIndex.setValue(n));
	}

}
