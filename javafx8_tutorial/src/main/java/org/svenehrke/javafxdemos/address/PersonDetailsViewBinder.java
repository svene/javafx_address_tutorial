package org.svenehrke.javafxdemos.address;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.controlsfx.dialog.Dialogs;
import org.svenehrke.javafxdemos.address.model.PersonAPI;
import org.svenehrke.javafxdemos.infra.ModelStore;
import org.svenehrke.javafxdemos.infra.PresentationModel;

public class PersonDetailsViewBinder {

	public static void bindView(PersonDetailsView view, Model model, ModelStore modelStore) {
		// Initialize the person table with the two columns.
		view.firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getAttribute(PersonAPI.ATT_FIRST_NAME).getValueProperty());
		view.lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getAttribute(PersonAPI.ATT_LAST_NAME).getValueProperty());

		view.newButton.setOnAction(event -> handleNewPerson(model, modelStore));
		view.editButton.setOnAction(event -> handleEditPerson(model) );
		view.deleteButton.setOnAction(event -> handleDelete(model) );

		PresentationModel cp = model.currentPerson;
		view.firstNameLabel.textProperty().bind(cp.getAttribute(PersonAPI.ATT_FIRST_NAME).getValueProperty());
		view.lastNameLabel.textProperty().bind(cp.getAttribute(PersonAPI.ATT_LAST_NAME).getValueProperty());
		view.streetLabel.textProperty().bind(cp.getAttribute(PersonAPI.ATT_STREET).getValueProperty());
		view.postalCodeLabel.textProperty().bind(cp.getAttribute(PersonAPI.ATT_POSTAL_CODE).getValueProperty());
		view.cityLabel.textProperty().bind(cp.getAttribute(PersonAPI.ATT_CITY).getValueProperty());
		view.birthdayLabel.textProperty().bind(cp.getAttribute(PersonAPI.ATT_BIRTHDAY).getValueProperty());

		// When model.selectedModelIndex changes: change selected row of table:
		model.selectedModelIndex.addListener((s,o,n) -> {
			view.personTable.getSelectionModel().select(model.selectedModelIndex.intValue());
		});
		ObservableList<PresentationModel> tableItems = new FilteredList<>(model.personPresentationModels, pm -> pm.hasTag(PersonAPI.TAG_REAL));
		view.personTable.setItems(tableItems);
		view.personTable.getSelectionModel().selectedIndexProperty().addListener((s, o, n) -> {
			String pmId = view.personTable.getSelectionModel().getSelectedItem().getId();
			model.selectedPmId.setValue(pmId);
			model.selectedModelIndex.setValue(n);
		});
	}

	private static void handleNewPerson(Model model, ModelStore modelStore) {
		model.workPerson.populateFromPresentationModel(modelStore.newEmptyPerson(), false);
		model.editModeProperty.setValue(Model.EditMode.NEW);
		PersonDialogs.showPersonDialog(model);
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 */
	private static void handleEditPerson(Model model) {
		if (model.currentPerson != null) {
			model.getWorkPerson().populateFromPresentationModel(model.currentPerson, false);
			model.editModeProperty.setValue(Model.EditMode.EDIT);
			PersonDialogs.showPersonDialog(model);
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

	private static void handleDelete(Model model) {
		int selectedIndex = model.selectedModelIndex.intValue();
		if (selectedIndex >= 0) {
			//todo: model.getPeople().remove(selectedIndex);
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
