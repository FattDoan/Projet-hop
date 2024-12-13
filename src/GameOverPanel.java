import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    GameConfig.WindowConfig window = ConfigManager.getInstance().getConfig().window;

    private Runnable restartListener;
    private Runnable exitListener;

    public void setRestartListener(Runnable listener) {
        this.restartListener = listener;
    }
    public void triggerRestart() {
        if (restartListener != null) {
            restartListener.run();
        }
    }

    public void setExitListener(Runnable listener) {
        this.exitListener = listener;
    }
    public void triggerExit() {
        if (exitListener != null) {
            exitListener.run();
        }
    }

    public GameOverPanel() {
        setPreferredSize(new Dimension(window.getWidth(), window.getHeight() - InfoBarPanel.PREF_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));

        JLabel gameOverLabel = new JLabel("Game Over!");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setPreferredSize(new Dimension(window.getWidth() - 100, 50));
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
        add(gameOverLabel);

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 24));
        restartButton.setPreferredSize(new Dimension(window.getWidth() - 200, 50));
        restartButton.addActionListener(e -> triggerRestart());
        add(restartButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setPreferredSize(new Dimension(window.getWidth() - 200, 50));
        exitButton.addActionListener(e -> triggerExit());
        add(exitButton);

        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
