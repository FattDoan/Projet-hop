import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    private Runnable startGameListener = Hop::startGame;
    private Runnable settingsListener = Hop::displaySettingsMenu;    
    private Runnable exitListener = () -> {
        System.exit(0);
    };
    
    public MainMenu() {
        setPreferredSize(new Dimension(Hop.windowC.getWidth(), Hop.windowC.getHeight()));
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));

        JLabel gameLabel = new JLabel("Hop!");
        gameLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gameLabel.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 100, 50));
        gameLabel.setHorizontalAlignment(JLabel.CENTER);
        add(gameLabel);

        JButton startGameButton = new JButton("Start game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 24));
        startGameButton.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 50));
        startGameButton.addActionListener(e -> Utils.runCode(startGameListener));
        add(startGameButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.BOLD, 24));
        settingsButton.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 50));
        settingsButton.addActionListener(e -> Utils.runCode(settingsListener));
        add(settingsButton);


        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 50));
        exitButton.addActionListener(e -> Utils.runCode(exitListener));
        add(exitButton);

        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
