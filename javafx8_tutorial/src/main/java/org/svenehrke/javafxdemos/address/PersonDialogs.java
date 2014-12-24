package org.svenehrke.javafxdemos.address;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PersonDialogs {

	public static void showPersonDialog(Model model, Stage primaryStage) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			URL resource = Main.class.getResource("/PersonEditDialog.fxml");
			final FXMLLoader loader = new FXMLLoader(resource, null);
			AnchorPane page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			PersonEditDialogView controller = loader.getController();
			PersonEditViewBinder.bindController(controller, dialogStage, model, model.workPerson); // todo: blog about this pattern: bindView (binding ...) not inside controller but outside of it. Similar to binding of PMs to widgets: not in view but outside

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
