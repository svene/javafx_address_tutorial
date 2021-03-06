package org.svenehrke.javafxdemos.address;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.svenehrke.javafxdemos.address.model.SampleData;
import org.svenehrke.javafxdemos.infra.*;

/**
 * Like 'Main' but the pane is loaded explicitly which allows to configure the controller (GreetController2 in this example) in the code
 * and not in the fxml.
 */
public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}

	@Override
	public void init() throws Exception {
	}

	@Override
	public void stop() throws Exception {
	}


	@Override
	public void start(Stage primaryStage) throws Exception {

		Model model = new Model(primaryStage);
		model.getPeople().addAll(SampleData.getPeople());
		model.getPrimaryStage().setTitle("AddressApp");

		model.getPrimaryStage().getIcons().add(new Image(this.getClass().getResourceAsStream("/Address_Book.png")));

		initRootLayout(model);
	}

	private void initRootLayout(Model model) {

		// Give the view access to the main app.
		final ViewAndRoot<RootLayoutView, BorderPane> cr = FXMLLoader2.loadFXML("/RootLayout.fxml");
		RootLayoutView view = cr.getView();
		BorderPane rootLayout = cr.getRoot();

		new RootLayoutViewBinder().bindView(view, model);
		model.getPrimaryStage().titleProperty().bind(model.applicationTitle);

		Scene scene = new Scene(rootLayout);
		model.getPrimaryStage().setScene(scene);
		model.getPrimaryStage().show();

		showPersonOverview(rootLayout, model);
	}

	private void showPersonOverview(BorderPane rootLayout, Model model) {
		final ViewAndRoot<PersonDetailsView, Pane> cr = FXMLLoader2.loadFXML("/PersonDetails.fxml");
		PersonDetailsView personDetailsView = cr.getView();
		PersonDetailsViewBinder.bindView(personDetailsView, model);
		rootLayout.setCenter(cr.getRoot());
	}

}
