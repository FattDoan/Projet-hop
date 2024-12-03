import javax.swing.*;
import java.awt.event.ActionEvent;

public class Hop {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 800;
    public static final int DELAY = 15;
	public static final int MAX_LEVEL = 7; // lucky number :D
	public static final int[] HEIGHT_LEVELS = {0, 80, 800, 2000, 3200, 4800, 7200, 10000};

    private final JFrame frame = new JFrame("Hop!");
    private Field field;
    private Axel axel;
    private Timer timer;
    private GamePanel gamePanel;
	private InfoBarPanel infoBarPanel;
	private GameOverPanel gameOverPanel;
	private boolean gameStarted;
	private int currentScore;
	private int currentLevel;

	private static Hop instance;

    public Hop() {
		this.currentLevel = 0;
		this.currentScore = 0;
		this.gameStarted = false;

		this.field = new Field(WIDTH, HEIGHT - InfoBarPanel.PREF_HEIGHT, this.currentLevel);
        this.axel = new Axel(field, WIDTH/2, Field.START_ALTITUDE);

		this.gamePanel = new GamePanel(field, axel);
		
		this.infoBarPanel = new InfoBarPanel();
		
		this.gameOverPanel = new GameOverPanel();
		this.gameOverPanel.setExitListener(() -> {
			System.exit(0);
		});
		this.gameOverPanel.setRestartListener(Hop::startGame);
		
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.add(infoBarPanel);
		frame.add(gamePanel);
		frame.addKeyListener(gamePanel);
		frame.setFocusable(true);
		frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	public static Hop getInstance() {
		if (instance == null) {
			instance = new Hop();
		}
		return instance;
	}
	public static void startGame() {
		Hop game = getInstance();

		game.currentLevel = 0;
		game.currentScore = 0;
		game.gameStarted = false;

		game.field = new Field(WIDTH, HEIGHT - InfoBarPanel.PREF_HEIGHT, game.currentLevel);
        game.axel = new Axel(game.field, WIDTH/2, Field.START_ALTITUDE);
   
		game.frame.getContentPane().remove(game.gameOverPanel);
		game.frame.getContentPane().remove(game.gamePanel);
		game.gamePanel = new GamePanel(game.field, game.axel);
		game.frame.add(game.gamePanel);
		game.frame.addKeyListener(game.gamePanel);
		game.frame.setFocusable(true);
		
		game.timer = new Timer(DELAY, (ActionEvent e) -> {
			game.round();
			if (game.over()) {
				game.timer.stop();

				game.frame.remove(game.gamePanel);
				game.frame.add(game.gameOverPanel);
				game.frame.revalidate();
				game.frame.repaint();
			}
		});
		game.timer.start();

	}
	private void updateLevel() {
		if (currentScore >= HEIGHT_LEVELS[currentLevel + 1] && currentLevel < MAX_LEVEL) {
			currentLevel++;
		}
	}
    public void round() {
		if (axel.isOnBlock() == true && axel.getY() != Field.START_ALTITUDE) {
			gameStarted = true;
		}
		if (axel.isOnBlock()) {
			currentScore = Math.max(currentScore, field.getPassedAltitude() + axel.getY());
			updateLevel();
		}
	    if (gameStarted) {
			field.update(currentLevel);
		}
		axel.update();
		infoBarPanel.update(currentScore, currentLevel);
        frame.repaint();
    }

    public boolean over() {
        return !axel.isSurviving();
    }
	
    public static void main(String[] args) {
		SwingUtilities.invokeLater(Hop::startGame);
	}

}
