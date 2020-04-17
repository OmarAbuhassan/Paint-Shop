package tes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Main extends Application {

	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage=primaryStage;
		

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/GUI.fxml"));
		controller controller = new controller();
        loader.setController( controller);
        Parent root = loader.load();
			primaryStage.setScene( new Scene(root));
		primaryStage.setMaximized(true);
		primaryStage.getIcons().add(new Image("Gui/paint.png"));
		primaryStage.setTitle("Painter Shop");
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent e) {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				

				alert.setTitle("Save your work");
				alert.setHeaderText("Do you want to save your changes ?");
				alert.setContentText("You may lose your last work session ! ");
				alert.getButtonTypes().clear();


				Optional<ButtonType> result;

				ButtonType saveButton = new ButtonType("Save changes");
				ButtonType noSaveButton = new ButtonType("Do not Save");
				ButtonType cancelButton = new ButtonType("Cancel");

				alert.getButtonTypes().add(saveButton);
				alert.getButtonTypes().add(noSaveButton);
				alert.getButtonTypes().add(cancelButton);

				result = alert.showAndWait();
				if (result.get() == saveButton) {
					controller.saveFile();
				}
				else if (result.get() == noSaveButton) {
					System.exit(0);
				}
				else{
					e.consume();

				}
			}
		});





	}

	public static Stage getStage(){

		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

