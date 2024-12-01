import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
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
		setPreferredSize(new Dimension(Hop.WIDTH, Hop.HEIGHT - InfoBarPanel.PREF_HEIGHT));
		setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));

		JLabel gameOverLabel = new JLabel("Game Over!");
		gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
		gameOverLabel.setPreferredSize(new Dimension(Hop.WIDTH - 100, 50));
		gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
		add(gameOverLabel);

		JButton restartButton = new JButton("Restart");
		restartButton.setFont(new Font("Arial", Font.BOLD, 24));
		restartButton.setPreferredSize(new Dimension(Hop.WIDTH - 200, 50));
		restartButton.addActionListener(e -> triggerRestart());
		add(restartButton);

		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Arial", Font.BOLD, 24));
		exitButton.setPreferredSize(new Dimension(Hop.WIDTH - 200, 50));
		exitButton.addActionListener(e -> triggerExit());
		add(exitButton);

		setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	@Override
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Continue with default painting
        super.paintComponent(g2);
    }

}
