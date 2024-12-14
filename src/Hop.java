import javax.swing.*;
import java.awt.event.ActionEvent;

public class Hop {
    /* Load the configuration file */
    public static ConfigManager configManager = ConfigManager.getInstance();
    public static GameConfig gameConfig = configManager.getConfig();
    
    public static GameConfig.WindowConfig windowC = gameConfig.window;
    public static GameConfig.GameRulesConfig gameRulesC = gameConfig.gameRules;
    public static GameConfig.BlockConfig blockC = gameConfig.block;
    public static GameConfig.AxelConfig axelC = gameConfig.axel;
    public static GameConfig.FireBallConfig fireBallC = gameConfig.fireBall;
    public static int DELAY = (int) 1000 / gameRulesC.getFps();

    /* JFrame (window) and sub-Menus */
    private final JFrame frame = new JFrame("Hop!");
    private MainMenu mainMenu;
    private GamePanel gamePanel;
    private SettingsMenu settingsMenu;
    private InfoBarPanel infoBarPanel;
    private GameOverPanel gameOverPanel;
    private PauseGamePanel pauseGamePanel; 
    /* Game objects and info */
    private Field field;
    private Axel axel;
    private boolean gameStarted;
    private int currentScore;
    private int currentLevel;

    /* There should be only one instance of the game Hop
     * This helps when we switch between scences (mainMenu, gameOverPanel, etc)
     * we can keep the current instance of the game Hop 
     * (no need to reinitialize the game every time we switch between scenes)
     */
    private static Hop instance;
    public static Hop getInstance() {
        if (instance == null) {
            instance = new Hop();
        }
        return instance;
    }
    
    // for the game loop
    private Timer timer;

    public Hop() {
        this.mainMenu = new MainMenu();      
        this.settingsMenu = new SettingsMenu();
        this.gameOverPanel = new GameOverPanel();
        this.pauseGamePanel = new PauseGamePanel();
        
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(mainMenu);
        frame.setFocusable(true);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void displaySettingsMenu() {
        Hop game = getInstance();
        game.frame.getContentPane().removeAll();
        game.frame.add(game.settingsMenu);
        game.frame.revalidate();
        game.frame.repaint();
    }
    public static void displayMainMenu() {
        Hop game = getInstance();
        game.frame.getContentPane().removeAll();
        game.frame.add(game.mainMenu);
        game.frame.revalidate();
        game.frame.repaint();
    }
    public static void displayPausePanel() {
        Hop game = getInstance();
        game.frame.getContentPane().remove(game.gamePanel);
        game.frame.add(game.pauseGamePanel);
        game.frame.addKeyListener(game.pauseGamePanel);
        game.frame.revalidate();
        game.frame.repaint();
    }
    public static void displayGameOverPanel() {
        Hop game = getInstance();
        game.frame.getContentPane().remove(game.gamePanel);
        game.frame.add(game.gameOverPanel);
        game.frame.revalidate();
        game.frame.repaint();
    }
    public static void startGame() {
        Hop game = getInstance();
        gameConfig = configManager.getConfig();

        // Reset the game state
        game.currentLevel = Hop.gameRulesC.getStartingLevel();
        game.currentScore = 0;
        game.gameStarted = false;

        game.updateLevel();
        game.field = new Field(Hop.windowC.getWidth(), Hop.windowC.getHeight() - InfoBarPanel.PREF_HEIGHT, game.currentLevel);
        game.axel = new Axel(game.field, Hop.windowC.getWidth()/2, Hop.blockC.getStartAltitude());

        game.frame.getContentPane().removeAll();
        
        // Add the infoBarPanel and gamePanel to the frame
        game.infoBarPanel = new InfoBarPanel();
        game.gamePanel = new GamePanel(game.field, game.axel);
        game.frame.add(game.infoBarPanel);
        game.frame.add(game.gamePanel);
        game.frame.addKeyListener(game.gamePanel);
        game.frame.setFocusable(true);
        game.frame.revalidate();
        game.frame.repaint();
        gameLoop(game);
    }
    public static void resumeGame() {
        Hop game = getInstance();
        GamePanel.isPaused = false;
        game.frame.getContentPane().removeAll();
        game.frame.add(game.infoBarPanel);
        game.frame.add(game.gamePanel);
        game.frame.addKeyListener(game.gamePanel);
        game.frame.setFocusable(true);
        game.frame.revalidate();
        game.frame.repaint();
        gameLoop(game);
    }
    // gameLoop must be a static method because it is called from another static method (startGame and resumeGame)
    public static void gameLoop(Hop game) {
        if (game.timer == null) {
            game.timer = new Timer(DELAY, (ActionEvent e) -> {
                // The game logic is handled in this main thread
                game.round();
                // The rendering is pushed to the EDT to take care of it (in a separate thread)
                SwingUtilities.invokeLater(() -> game.frame.repaint());
                if (game.over()) {
                    game.timer.stop();
                    displayGameOverPanel();
                }
                if (GamePanel.isPaused) {
                    game.timer.stop();
                    displayPausePanel();
                }
            });
        }
        game.timer.start();
    }
    // while currentLevel is not max level
    // and currentScore is greater than the height needed to reach the next level
    private void updateLevel() {
        while (currentLevel < Hop.gameRulesC.getMaxLevel() && 
                currentScore >= Utils.at(Hop.gameRulesC.getHeightToReachNextLevel(), currentLevel)) {
            currentLevel++;
        }
    }
    private void round() {
        if (axel.isOnBlock() == true && axel.getY() != Hop.blockC.getStartAltitude()) {
            gameStarted = true;
        }
        if (axel.isOnBlock()) {
            currentScore = Math.max(currentScore, field.getPassedAltitude(currentLevel) + axel.getY());
            updateLevel();
        }
        if (gameStarted) {
            field.update(currentLevel);
        }
        axel.update();
        infoBarPanel.update(currentScore, currentLevel);
    }

    private boolean over() {
        return !axel.isSurviving();
    }

    public static void main(String[] args) {
        // Enabling anti-aliasing for better text rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(Hop::displayMainMenu);
    }

}
