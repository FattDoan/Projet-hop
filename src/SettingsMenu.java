import java.awt.*;
import javax.swing.*;

class SettingSpinner extends JPanel {
    private JLabel label;
    private JSpinner spinner;
    public SettingSpinner(String labelText, int curVal, int min, int max, int step) {
        label = new JLabel(labelText);
        spinner = new JSpinner(new SpinnerNumberModel(curVal, min, max, step));
        add(label);
        add(spinner);
    }
    public JSpinner getSpinner() {
        return spinner;
    }
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Continue with default painting
        super.paintComponent(g2);
    }

}
class SettingBoolean extends JPanel {
    private JLabel label;
    private JComboBox<Boolean> booleanBox;
    public SettingBoolean(String labelText, boolean curVal) {
        label = new JLabel(labelText);
        booleanBox = new JComboBox<>(new Boolean[] {true, false});
        booleanBox.setSelectedItem(curVal);
        add(label);
        add(booleanBox);
    }
    public JComboBox<Boolean> getBooleanBox() {
        return booleanBox;
    }   
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Continue with default painting
        super.paintComponent(g2);
    }
}
public class SettingsMenu extends JPanel {
    private ConfigManager configManager = ConfigManager.getInstance();

    private GameConfig.WindowConfig windowC = configManager.getConfig().window;
    private GameConfig.GameRulesConfig gameRulesC = configManager.getConfig().gameRules;
    private GameConfig.AxelConfig axelC = configManager.getConfig().axel;
    private GameConfig.BlockConfig blockC = configManager.getConfig().block;

    private Runnable backListener;
    private Runnable saveListener;

    private SettingSpinner startingLevelSpinner, maxNumJumpsSpinner, altitudeGapSpinner;
    private SettingBoolean enableFireBalls;

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
        int newStartingLevel = (int) startingLevelSpinner.getSpinner().getValue();
        int newMaxNumJumps = (int) maxNumJumpsSpinner.getSpinner().getValue();
        int newAltitudeGap = (int) altitudeGapSpinner.getSpinner().getValue();
        boolean newEnableFireBalls = (boolean) enableFireBalls.getBooleanBox().getSelectedItem();
        
        gameRulesC.setStartingLevel(newStartingLevel);
        axelC.setMaxNumJumps(newMaxNumJumps);
        blockC.setAltitudeGap(newAltitudeGap);
        gameRulesC.setFireBallsEnabled(newEnableFireBalls);
   
        configManager.saveConfig();
        System.out.println("Settings saved");
    }

    public SettingsMenu() {
        setPreferredSize(new Dimension(windowC.getWidth(), windowC.getHeight()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 32));
        settingsLabel.setPreferredSize(new Dimension(windowC.getWidth() - 100, 50));
        settingsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(settingsLabel);

        startingLevelSpinner = new SettingSpinner("Starting level: ", 
                                                  gameRulesC.getStartingLevel(), 
                                                  1,
                                                  gameRulesC.getMaxLevel(),
                                                  1);
        add(startingLevelSpinner);
        
        maxNumJumpsSpinner = new SettingSpinner("Max number of jumps (2 for double jumps, 3 for triple): ", 
                                                 axelC.getMaxNumJumps(), 
                                                 1,
                                                 5,
                                                 1);
        add(maxNumJumpsSpinner);

        altitudeGapSpinner = new SettingSpinner("Altitude gap between blocks : ", 
                                                blockC.getAltitudeGap(), 
                                                10,
                                                windowC.getHeight() / 2,
                                                10);
        add(altitudeGapSpinner);

        enableFireBalls = new SettingBoolean("Enable fireballs: ", 
                                              gameRulesC.isFireBallsEnabled());
        add(enableFireBalls);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setPreferredSize(new Dimension(windowC.getWidth() - 200, 50));
        backButton.addActionListener(e -> triggerBack());
        add(backButton);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 24));
        saveButton.setPreferredSize(new Dimension(windowC.getWidth() - 200, 50));
        saveButton.addActionListener(e -> triggerSave());
        add(saveButton);
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
