import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    private GameConfig.WindowConfig window = ConfigManager.getInstance().getConfig().window;

    private Runnable startGameListener;
    private Runnable settingsListener;
    private Runnable exitListener;

    public void setStartGameListener(Runnable listener) {
        this.startGameListener = listener;
    }
    public void triggerStartGame() {
        if (startGameListener != null) {
            startGameListener.run();
        }
    }

    public void setSettingsListener(Runnable listener) {
        this.settingsListener = listener;
    }
    public void triggerSettings() {
        if (settingsListener != null) {
            settingsListener.run();
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

    public MainMenu() {
        setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));

        JLabel gameLabel = new JLabel("Hop!");
        gameLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gameLabel.setPreferredSize(new Dimension(window.getWidth() - 100, 50));
        gameLabel.setHorizontalAlignment(JLabel.CENTER);
        add(gameLabel);

        JButton startGameButton = new JButton("Start game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 24));
        startGameButton.setPreferredSize(new Dimension(window.getWidth() - 200, 50));
        startGameButton.addActionListener(e -> triggerStartGame());
        add(startGameButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.BOLD, 24));
        settingsButton.setPreferredSize(new Dimension(window.getWidth() - 200, 50));
        settingsButton.addActionListener(e -> triggerSettings());
        add(settingsButton);


        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setPreferredSize(new Dimension(window.getWidth() - 200, 50));
        exitButton.addActionListener(e -> triggerExit());
        add(exitButton);

        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
