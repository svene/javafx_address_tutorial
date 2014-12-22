package org.svenehrke.javafxdemos.address.commandhandler;

import javafx.stage.Stage;
import org.svenehrke.javafxdemos.address.Model;

public class NewPersonCommandHandler implements Runnable {

	private final Stage primaryStage;
	private final Model model;

	public NewPersonCommandHandler(Stage primaryStage, Model model) {
		this.primaryStage = primaryStage;
		this.model = model;
	}

	@Override
	public void run() {
		handleNewPerson(model, primaryStage);
	}

	private static void handleNewPerson(Model model1, Stage primaryStage1) {
		model1.workPerson.populateFromPerson(model1.emptyPerson);
		PersonDialogs.showPersonDialog(model1, model1.newOkButtonClicked, primaryStage1);
	}

}
