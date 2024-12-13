import javax.swing.*;
import java.awt.event.ActionEvent;

public class Hop {
    public static ConfigManager configManager = ConfigManager.getInstance();
    public static GameConfig gameConfig = configManager.getConfig();
    
    public static GameConfig.WindowConfig windowC = gameConfig.window;
    public static GameConfig.GameRulesConfig gameRulesC = gameConfig.gameRules;
    public static GameConfig.BlockConfig blockC = gameConfig.block;
    public static GameConfig.AxelConfig axelC = gameConfig.axel;
    public static GameConfig.FireBallConfig fireBallC = gameConfig.fireBall;
    public static int DELAY = (int) 1000 / gameRulesC.getFps();

    private final JFrame frame = new JFrame("Hop!");
    private Field field;
    private Axel axel;
    private Timer timer;
    private MainMenu mainMenu;
    private GamePanel gamePanel;
    private SettingsMenu settingsMenu;
    private InfoBarPanel infoBarPanel;
    private GameOverPanel gameOverPanel;
    
    private boolean gameStarted;
    private int currentScore;
    private int currentLevel;

    private static Hop instance;

    public Hop() {
        this.mainMenu = new MainMenu();
        this.mainMenu.setStartGameListener(Hop::startGame);
        this.mainMenu.setSettingsListener(Hop::startSettings);
        this.mainMenu.setExitListener(() -> {
            System.exit(0);
        });
       
        this.settingsMenu = new SettingsMenu();
        this.settingsMenu.setBackListener(Hop::startApp);


        this.gameOverPanel = new GameOverPanel();
        this.gameOverPanel.setExitListener(() -> {
            System.exit(0);
        });
        this.gameOverPanel.setRestartListener(Hop::startGame);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(mainMenu);
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
    public static void startSettings() {
        Hop game = getInstance();
        game.frame.getContentPane().removeAll();
        game.frame.add(game.settingsMenu);
        game.frame.revalidate();
        game.frame.repaint();
    }
    public static void startApp() {
        Hop game = getInstance();
        game.frame.getContentPane().removeAll();
        game.frame.add(game.mainMenu);
        game.frame.revalidate();
        game.frame.repaint();
    }
    public static void startGame() {
        Hop game = getInstance();
        gameConfig = configManager.getConfig();

        game.currentLevel = Hop.gameRulesC.getStartingLevel();
        game.currentScore = 0;
        game.gameStarted = false;

        game.updateLevel();
        game.field = new Field(Hop.windowC.getWidth(), Hop.windowC.getHeight() - InfoBarPanel.PREF_HEIGHT, game.currentLevel);
        game.axel = new Axel(game.field, Hop.windowC.getWidth()/2, Hop.blockC.getStartAltitude());

        game.frame.getContentPane().removeAll();
        
        game.infoBarPanel = new InfoBarPanel();
        game.gamePanel = new GamePanel(game.field, game.axel);
        game.frame.add(game.infoBarPanel);
        game.frame.add(game.gamePanel);
        game.frame.addKeyListener(game.gamePanel);
        game.frame.setFocusable(true);
        game.frame.revalidate();
    
        /* Legacy game loop
         * game.timer = new Timer(DELAY, (ActionEvent e) -> {
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
        game.frame.repaint();
        */
        gameLoop(game);
    }
    public static void gameLoop(Hop game) {
        Thread gameLoopThread = new Thread(() -> {
        while (!game.over()) { 
            game.round();  
            SwingUtilities.invokeLater(() -> game.frame.repaint()); // Ensure UI update happens on the EDT 
            try {
                Thread.sleep(DELAY); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (game.over()) {
                SwingUtilities.invokeLater(() -> {
                    game.frame.remove(game.gamePanel);
                    game.frame.add(game.gameOverPanel);
                    game.frame.revalidate();
                    game.frame.repaint();
                });
            }
        }
    });
    gameLoopThread.start(); 
    }
    private void updateLevel() {
        while (currentLevel < Hop.gameRulesC.getMaxLevel() && 
                currentScore >= Utils.at(Hop.gameRulesC.getHeightToReachNextLevel(), currentLevel)) {
            currentLevel++;
        }
    }
    public void round() {
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

    public boolean over() {
        return !axel.isSurviving();
    }

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(Hop::startApp);
    }

}
