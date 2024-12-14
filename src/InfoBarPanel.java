import javax.swing.*;
import java.awt.*;
import javax.swing.border.MatteBorder;

public class InfoBarPanel extends JPanel {
    public static final int PREF_HEIGHT = 50;
    private JLabel scoreLabel = new JLabel("Score: 0");
    private JLabel levelLabel = new JLabel("Difficulty: 0");
    public InfoBarPanel() {
        setPreferredSize(new Dimension(Hop.windowC.getWidth(), PREF_HEIGHT));
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
    public void update(int score, int level) {
        scoreLabel.setText("Score: " + score);
        levelLabel.setText("Difficulty: " + level);
    }   
}

