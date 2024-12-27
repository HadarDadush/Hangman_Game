import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// Controller class for handling UI interactions.
public class HangmanController {

	@FXML
	private Label wordLabel;
	@FXML
	private TextField guessField;
	@FXML
	private Label incorrectGuessesLabel;
	@FXML
	private Label attemptsLeftLabel;
	@FXML
	private Canvas hangmanCanvas;

	private HangmanGame game;
	private HangmanGraphics graphics;

	private boolean isFirstGame = true;

	// Initialize the game when the app first loads.
	public void initialize() {
		startNewGame();
	}

	@FXML
	// Handle the user's guess input by delegating to the game logic.
	private void handleGuess() {
		String guess = guessField.getText();
		try {

			// Delegate the handling of the guess to the game logic.
			boolean correct = game.handleGuess(guess);

			// Draw the hangman if the guess is incorrect.
			if (!correct) {
				graphics.drawHangman(game.getAttemptsLeft());
			}
			updateUI();

			// Show game over dialog if the game is over.
			if (game.isGameOver()) {
				showGameOverDialog();
			}
		} catch (IllegalArgumentException e) {
			showInvalidInputAlert();
		}
		guessField.clear();
	}

	// Update the UI with the current game state.
	private void updateUI() {
		String currentGuessWithSpaces = game.getCurrentGuess().replaceAll("", " ").trim();
		wordLabel.setText(currentGuessWithSpaces);
		incorrectGuessesLabel.setText("Incorrect guesses: " + game.getIncorrectGuesses());
		attemptsLeftLabel.setText("Attempts left: " + game.getAttemptsLeft());
	}

	// Show a dialog when the game is over.
	private void showGameOverDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game Over");

		// Set message depending on whether the player won or lost.
		if (game.getCurrentGuess().equals(game.getWordToGuess())) {
			alert.setHeaderText("Congratulations! You won!");
			alert.setContentText(
					"You guessed the word: " + game.getWordToGuess() + "\nWould you like to start a new game?");
		} else {
			alert.setHeaderText("Game Over!");
			alert.setContentText("The word was: " + game.getWordToGuess() + "\nWould you like to start a new game?");
		}

		// Set buttons for starting a new game or exiting.
		ButtonType startNewGameButton = new ButtonType("Start new game");
		ButtonType exitButton = new ButtonType("Exit");
		alert.getButtonTypes().setAll(startNewGameButton, exitButton);

		alert.showAndWait().ifPresent(response -> {
			if (response == startNewGameButton) {
				startNewGame();
			} else if (response == exitButton) {
				System.exit(0);
			}
		});
	}

	// Start a new game and reset the game state.
	private void startNewGame() {
		game = new HangmanGame();
		graphics = new HangmanGraphics(hangmanCanvas);
		graphics.getGc().clearRect(0, 0, hangmanCanvas.getWidth(), hangmanCanvas.getHeight());
		if (isFirstGame) {
			showWelcomeMessage();
			isFirstGame = false;
		}

		updateUI();
	}

	// Show a welcome message only on the first game start.
	private void showWelcomeMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(" ");
		alert.setHeaderText("Welcome to Hangman Game!");
		alert.setContentText("Let's start the game!\n\n" + "The word file for the game is words.txt and"
				+ " it is stored in the bin directory.");
		alert.setGraphic(null);
		alert.showAndWait();
	}

	// Show an alert for invalid input (non-letter guess).
	private void showInvalidInputAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Invalid Input");
		alert.setHeaderText("Invalid Guess!");
		alert.setContentText("Please enter one letter.");
		alert.showAndWait();
	}
}