import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PauseGamePanel extends JPanel implements KeyListener {
    private Runnable resumeListener = Hop::resumeGame;
    private Runnable mainMenuListener = Hop::displayMainMenu;

    public PauseGamePanel() {
        setPreferredSize(new Dimension(Hop.windowC.getWidth(), Hop.windowC.getHeight() - InfoBarPanel.PREF_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));

        JLabel gameOverLabel = new JLabel("Paused");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 100, 50));
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
        add(gameOverLabel);

        JButton resumeButton = new JButton("Resume");
        resumeButton.setFont(new Font("Arial", Font.BOLD, 24));
        resumeButton.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 50));
        resumeButton.addActionListener(e -> Utils.runCode(resumeListener));

        add(resumeButton);
        
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 24));
        mainMenuButton.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 50));
        mainMenuButton.addActionListener(e -> Utils.runCode(mainMenuListener));
        add(mainMenuButton);

        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Utils.runCode(resumeListener);
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
