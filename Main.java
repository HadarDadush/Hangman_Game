import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Main entry point for the Hangman game application.
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Load the FXML file for the UI.
		Parent root = FXMLLoader.load(getClass().getResource("Hangman.fxml"));

		// Set the title and scene for the main window.
		primaryStage.setTitle("Hangman Game");
		primaryStage.setScene(new Scene(root));

		// Show the window.
		primaryStage.show();
	}

	public static void main(String[] args) {

		// Launch the application.
		launch(args);
	}
}