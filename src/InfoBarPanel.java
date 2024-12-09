import javax.swing.*;
import java.awt.*;
import javax.swing.border.MatteBorder;

public class InfoBarPanel extends JPanel {
	GameConfig.WindowConfig window = ConfigManager.getInstance().getConfig().window;
	public static final int PREF_HEIGHT = 50;
	private JLabel scoreLabel = new JLabel("Score: 0");
	private JLabel levelLabel = new JLabel("Difficulty: 0");
	public InfoBarPanel() {
		setPreferredSize(new Dimension(window.getWidth(), PREF_HEIGHT));
		setLayout(new GridLayout(1,2));
		setBackground(Color.LIGHT_GRAY);
		setBorder(new MatteBorder(0, 0, 3, 0, Color.BLUE));
		this.add(scoreLabel);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));

		this.add(levelLabel);
		levelLabel.setHorizontalAlignment(JLabel.CENTER);
		levelLabel.setFont(new Font("Arial", Font.BOLD, 24));
	}
    @Override
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Continue with default painting
        super.paintComponent(g2);
    }
	public void update(int score, int level) {
		scoreLabel.setText("Score: " + score);
		levelLabel.setText("Difficulty: " + level);
	}	
}

