import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    private Runnable restartListener = Hop::startGame;
    private Runnable exitListener = () -> {
        System.exit(0);
    };
    private Runnable mainMenuListener = Hop::displayMainMenu;

    public GameOverPanel() {
        setPreferredSize(new Dimension(Hop.windowC.getWidth(), Hop.windowC.getHeight() - InfoBarPanel.PREF_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));

        JLabel gameOverLabel = new JLabel("Game Over!");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 100, 50));
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
        add(gameOverLabel);

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 24));
        restartButton.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 50));
        restartButton.addActionListener(e -> Utils.runCode(restartListener));

        add(restartButton);
        
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 24));
        mainMenuButton.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 50));
        mainMenuButton.addActionListener(e -> Utils.runCode(mainMenuListener));
        add(mainMenuButton);


        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 50));
        exitButton.addActionListener(e -> Utils.runCode(exitListener));
        add(exitButton);

        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
