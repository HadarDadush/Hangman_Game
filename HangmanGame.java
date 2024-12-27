import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Logic for the Hangman game. 
// Handles word selection, guess processing, and game state tracking.
public class HangmanGame {
	private String wordToGuess;
	private StringBuilder currentGuess;
	private int attemptsLeft;
	private List<String> incorrectGuesses;
	private List<String> correctGuesses;

	public HangmanGame() {
		List<String> words = loadWordsFromFile("bin/words.txt");
		wordToGuess = words.get((int) (Math.random() * words.size())).toLowerCase();
		currentGuess = new StringBuilder();
		incorrectGuesses = new ArrayList<>();
		correctGuesses = new ArrayList<>();
		attemptsLeft = 9;

		// Initialize the current guess with underscores.
		for (int i = 0; i < wordToGuess.length(); i++) {
			currentGuess.append("_");
		}
	}

	private List<String> loadWordsFromFile(String filePath) {
		List<String> words = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				words.add(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
	}

	public String getCurrentGuess() {
		return currentGuess.toString();
	}

	public String getWordToGuess() {
		return wordToGuess;
	}

	public String getIncorrectGuesses() {

		// Convert incorrect guesses to lowercase and join them into a string.
		List<String> lowerCaseIncorrectGuesses = incorrectGuesses.stream().map(String::toLowerCase)
				.collect(Collectors.toList());
		return String.join(", ", lowerCaseIncorrectGuesses);
	}

	public int getAttemptsLeft() {
		return attemptsLeft;
	}

	public boolean handleGuess(String guess) {
		
		// Validate the input.
		if (guess.length() != 1 || !guess.matches("[a-zA-Z]")) {
			throw new IllegalArgumentException("Invalid input. Please enter a single letter.");
		}

		// Process the guess.
		return guessLetter(guess.toLowerCase());
	}

	private boolean guessLetter(String guessedLetter) {

		// Check if the letter has already been guessed.
		if (incorrectGuesses.contains(guessedLetter) || correctGuesses.contains(guessedLetter)) {
			return false;
		}

		// If the guess is correct, update the current guess.
		if (wordToGuess.contains(guessedLetter)) {
			correctGuesses.add(guessedLetter);
			for (int i = 0; i < wordToGuess.length(); i++) {
				if (wordToGuess.charAt(i) == guessedLetter.charAt(0)) {
					currentGuess.setCharAt(i, guessedLetter.charAt(0));
				}
			}
			return true;
		} else {

			// If the guess is incorrect, decrease attempts and add to incorrect guesses.
			incorrectGuesses.add(guessedLetter);
			attemptsLeft--;
			return false;
		}
	}

	public boolean isGameOver() {

		// Game is over when no attempts are left or the word is fully guessed.
		return attemptsLeft == 0 || currentGuess.toString().equals(wordToGuess);
	}
}
