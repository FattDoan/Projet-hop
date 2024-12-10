import javax.swing.*;
import java.awt.event.ActionEvent;

public class Hop {
    private GameConfig.WindowConfig window = ConfigManager.getInstance().getConfig().window;
    private GameConfig.GameRulesConfig gameRules = ConfigManager.getInstance().getConfig().gameRules;
    private GameConfig.BlockConfig block = ConfigManager.getInstance().getConfig().block;

    private final JFrame frame = new JFrame("Hop!");
    private Field field;
    private Axel axel;
    private Timer timer;
    private MainMenu mainMenu;
    private GamePanel gamePanel;
    private InfoBarPanel infoBarPanel;
    private GameOverPanel gameOverPanel;
    
    private boolean gameStarted;
    private int currentScore;
    private int currentLevel;

    private static Hop instance;

    public Hop() {
        this.mainMenu = new MainMenu();
        this.mainMenu.setStartGameListener(Hop::startGame);
        this.mainMenu.setSettingsListener(() -> {
            System.out.println("Settings");
        });
        this.mainMenu.setExitListener(() -> {
            System.exit(0);
        });
        
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
    public static void startApp() {
        Hop game = getInstance();
        game.frame.getContentPane().removeAll();
        game.frame.add(game.mainMenu);
        game.frame.revalidate();
    }
    public static void startGame() {
        Hop game = getInstance();
        game.currentLevel = game.gameRules.getStartingLevel();
        game.currentScore = 0;
        game.gameStarted = false;

        game.updateLevel();
        game.field = new Field(game.window.getWidth(), game.window.getHeight() - InfoBarPanel.PREF_HEIGHT, game.currentLevel);
        game.axel = new Axel(game.field, game.window.getWidth()/2, game.block.getStartAltitude());

        game.frame.getContentPane().removeAll();
        
        game.infoBarPanel = new InfoBarPanel();
        game.gamePanel = new GamePanel(game.field, game.axel);
        game.frame.add(game.infoBarPanel);
        game.frame.add(game.gamePanel);
        game.frame.addKeyListener(game.gamePanel);
        game.frame.setFocusable(true);
        game.frame.revalidate();
         
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
                currentScore >= Utils.at(gameRules.getHeightToReachNextLevel(), currentLevel)) {
            currentLevel++;
        }
    }
    public void round() {
        if (axel.isOnBlock() == true && axel.getY() != block.getStartAltitude()) {
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
        frame.repaint();
    }

    public boolean over() {
        return !axel.isSurviving();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Hop::startApp);
    }

}
