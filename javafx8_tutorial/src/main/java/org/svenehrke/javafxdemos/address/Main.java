package org.svenehrke.javafxdemos.address;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.svenehrke.javafxdemos.address.model.Person;

import java.io.IOException;
import java.net.URL;

/**
 * Like 'Main' but the pane is loaded explicitly which allows to configure the controller (GreetController2 in this example) in the code
 * and not in the fxml.
 */
public class Main extends Application {

	private PersonOverviewController personOverviewController;

	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}

	private Stage primaryStage;
	private BorderPane rootLayout;
	private IApplicationEventHandler applicationEventHandler;
	private Model model;

	@Override
	public void init() throws Exception {
		model = new Model();
	}

	@Override
	public void stop() throws Exception {
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");

		initRootLayout();
		showPersonOverview();

		applicationEventHandler = new ApplicationEventHandlerImpl(model, primaryStage, personOverviewController);
		personOverviewController.setApplicationEventHandler(applicationEventHandler);
	}

	private void initRootLayout() {
		URL resource = Main.class.getResource("/RootLayout.fxml");
		final FXMLLoader loader = new FXMLLoader(resource, null);
		try {
			rootLayout = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void showPersonOverview() {
		URL resource = Main.class.getResource("/PersonOverview.fxml");
		final FXMLLoader loader = new FXMLLoader(resource, null);
		AnchorPane personOverview;
		try {
			personOverview = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		personOverviewController = loader.getController();
		personOverviewController.setModel(model);
		rootLayout.setCenter(personOverview);

	}

}
