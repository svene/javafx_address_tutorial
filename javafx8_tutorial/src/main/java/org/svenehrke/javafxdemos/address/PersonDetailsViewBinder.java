package org.svenehrke.javafxdemos.address;

import javafx.beans.binding.Bindings;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import org.svenehrke.javafxdemos.address.model.Person;
import org.svenehrke.javafxdemos.infra.Mate;

public class PersonDetailsViewBinder {

	public static void bindController(PersonDetailsView view, Mate mate) {
		// Initialize the person table with the two columns.
		view.firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		view.lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		view.newButton.setOnAction(event -> handleNewPerson(mate.getPrimaryStage(), mate.getModel()));
		view.editButton.setOnAction(event -> handleEditPerson(mate.getPrimaryStage(), mate.getModel()) );
		view.deleteButton.setOnAction(event -> handleDelete(mate.getModel()) );

		Person cp = mate.getModel().currentPerson;
		view.firstNameLabel.textProperty().bind(cp.firstNameProperty());
		view.lastNameLabel.textProperty().bind(cp.lastNameProperty());
		view.streetLabel.textProperty().bind(cp.streetProperty());
		view.postalCodeLabel.textProperty().bind(Bindings.convert(cp.postalCodeProperty()));
		view.cityLabel.textProperty().bind(cp.cityProperty());
		view.birthdayLabel.textProperty().bind(Bindings.convert(cp.birthdayProperty()));

		// When model.selectedModelIndex changes: change selected row of table:
		mate.getModel().selectedModelIndex.addListener((s,o,n) -> {
			view.personTable.getSelectionModel().select(mate.getModel().selectedModelIndex.intValue());
		});

		view.personTable.setItems(mate.getModel().getPersonData());
		view.personTable.getSelectionModel().selectedIndexProperty().addListener((s, o, n) -> mate.getModel().selectedModelIndex.setValue(n));
	}

	private static void handleNewPerson(Stage primaryStage1, Model model1) {
		model1.workPerson.populateFromPerson(model1.emptyPerson);
		model1.editModeProperty.setValue(Model.EditMode.NEW);
		PersonDialogs.showPersonDialog(model1, primaryStage1);
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 */
	private static void handleEditPerson(Stage primaryStage1, Model model1) {
		if (model1.currentPerson != null) {
			model1.getWorkPerson().populateFromPerson(model1.currentPerson);
			model1.editModeProperty.setValue(Model.EditMode.EDIT);
			PersonDialogs.showPersonDialog(model1, primaryStage1);
		} else {
			// Nothing selected.
			System.out.println("nothing selected");
			Dialogs.create()
				.title("No Selection")
				.masthead("No Person Selected")
				.message("Please select a person in the table.")
				.showWarning();
		}
	}

	private static void handleDelete(Model model1) {
		int selectedIndex = model1.selectedModelIndex.intValue();
		if (selectedIndex >= 0) {
			model1.getPersonData().remove(selectedIndex);
		} else {
			// Nothing selected.
			Dialogs.create()
				.title("No Selection")
				.masthead("No Person Selected")
				.message("Please select a person in the table.")
				.showWarning();
		}

	}
}
