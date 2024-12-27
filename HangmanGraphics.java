import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

// Handles drawing the hangman graphics based on attempts left.
public class HangmanGraphics {

	private GraphicsContext gc;

	public HangmanGraphics(Canvas canvas) {
		this.gc = canvas.getGraphicsContext2D();
	}

	public void drawHangman(int attemptsLeft) {
		gc.setLineWidth(4);

		// Draw different parts of the hangman based on attempts left.
		switch (attemptsLeft) {
		case 8:
			gc.strokeLine(50, 50, 50, 150); // Pole
			break;
		case 7:
			gc.strokeLine(50, 50, 100, 50); // Top bar
			break;
		case 6:
			gc.strokeLine(100, 50, 100, 75); // Vertical bar
			break;
		case 5:
			gc.strokeOval(90, 75, 20, 20); // Head
			break;
		case 4:
			gc.strokeLine(100, 95, 100, 130); // Body
			break;
		case 3:
			gc.strokeLine(100, 130, 80, 160); // Left leg
			break;
		case 2:
			gc.strokeLine(100, 130, 120, 160); // Right leg
			break;
		case 1:
			gc.strokeLine(100, 110, 80, 90); // Left arm
			break;
		case 0:
			gc.strokeLine(100, 110, 120, 90); // Right arm
			break;
		}
	}

	public GraphicsContext getGc() {
		return gc;
	}

}
