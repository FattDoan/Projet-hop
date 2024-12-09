import javax.swing.*;
import java.awt.event.ActionEvent;

public class Hop {
	private GameConfig.WindowConfig window = ConfigManager.getInstance().getConfig().window;
    private GameConfig.gameRulesConfig gameRules = ConfigManager.getInstance().getConfig().gameRules;
	
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
		this.currentLevel = gameRules.getStartingLevel();
		this.currentScore = gameRules.getStartingScore();
		this.gameStarted = false;

		this.updateLevel();
		this.field = new Field(window.getWidth(), window.getHeight() - InfoBarPanel.PREF_HEIGHT, this.currentLevel);
        this.axel = new Axel(field, window.getWidth()/2, Field.START_ALTITUDE);

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
	public void resetGameState(Hop game) {
		//game != null
		game.currentLevel = gameRules.getStartingLevel();
		game.currentScore = gameRules.getStartingScore();
		game.gameStarted = false;

		game.updateLevel();
		game.field = new Field(window.getWidth(), window.getHeight() - InfoBarPanel.PREF_HEIGHT, game.currentLevel);
		game.axel = new Axel(game.field, window.getWidth()/2, Field.START_ALTITUDE);
   
		game.frame.getContentPane().remove(game.gameOverPanel);
		game.frame.getContentPane().remove(game.gamePanel);
		game.gamePanel = new GamePanel(game.field, game.axel);
		game.frame.add(game.gamePanel);
		game.frame.addKeyListener(game.gamePanel);
		game.frame.setFocusable(true);
		game.frame.revalidate();
	}
	public static void startGame() {
		//Hop game = getInstance();
		Hop game;
		if (instance == null) {
			instance = new Hop();
			game = instance;
		}
		else {
			game = instance;
			game.resetGameState(game);
		}
		int DELAY = (int) 1000/game.gameRules.getFps(); 
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
		while (currentLevel < gameRules.getMaxLevel() && 
			   currentScore >= gameRules.getHeightToReachNextLevel()[currentLevel + 1]) {
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
