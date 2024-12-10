import java.awt.*;
import javax.swing.*;

public class SettingsMenu extends JPanel {
    private GameConfig.WindowConfig window = ConfigManager.getInstance().getConfig().window;

    private Runnable backListener;
    private Runnable saveListener;

    public void setBackListener(Runnable listener) {
        this.backListener = listener;
    }
    public void triggerBack() {
        if (backListener != null) {
            backListener.run();
        }
    }

    public void setSaveListener(Runnable listener) {
        this.saveListener = listener;
    }
    public void triggerSave() {
        if (saveListener != null) {
            saveListener.run();
        }
    }

    public SettingsMenu() {
        setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 32));
        settingsLabel.setPreferredSize(new Dimension(window.getWidth() - 100, 50));
        settingsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(settingsLabel);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setPreferredSize(new Dimension(window.getWidth() - 200, 50));
        backButton.addActionListener(e -> triggerBack());
        add(backButton);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 24));
        saveButton.setPreferredSize(new Dimension(window.getWidth() - 200, 50));
        saveButton.addActionListener(e -> triggerSave());
        add(saveButton);
    }
}
