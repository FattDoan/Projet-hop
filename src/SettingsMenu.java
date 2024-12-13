import java.awt.*;
import javax.swing.*;

class SettingSpinner extends JPanel {
    private JLabel label;
    private JSpinner spinner;
    public SettingSpinner(String labelText, int curVal, int min, int max, int step) {
        label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        spinner = new JSpinner(new SpinnerNumberModel(curVal, min, max, step));
        spinner.setPreferredSize(new Dimension(80, 36)); 
        spinner.setFont(new Font("Arial", Font.PLAIN, 20)); 
        add(label);
        add(spinner);
    }
    public JSpinner getSpinner() {
        return spinner;
    }
}
class SettingBoolean extends JPanel {
    private JLabel label;
    private JComboBox<Boolean> booleanBox;
    public SettingBoolean(String labelText, boolean curVal) {
        label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        booleanBox = new JComboBox<>(new Boolean[] {true, false});
        booleanBox.setPreferredSize(new Dimension(80, 36)); 
        booleanBox.setFont(new Font("Arial", Font.PLAIN, 20)); 
        booleanBox.setSelectedItem(curVal);
        add(label);
        add(booleanBox);
    }
    public JComboBox<Boolean> getBooleanBox() {
        return booleanBox;
    }   
}
public class SettingsMenu extends JPanel {
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
        
        Hop.gameRulesC.setStartingLevel(newStartingLevel);
        Hop.axelC.setMaxNumJumps(newMaxNumJumps);
        Hop.blockC.setAltitudeGap(newAltitudeGap);
        Hop.gameRulesC.setFireBallsEnabled(newEnableFireBalls);
   
        Hop.configManager.saveConfig();
        System.out.println("Settings saved");
    }

    public SettingsMenu() {
        setPreferredSize(new Dimension(Hop.windowC.getWidth(), Hop.windowC.getHeight()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(Box.createVerticalGlue());

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 32));
        settingsLabel.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 100, 50));
        settingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);       
        add(settingsLabel);


        add(Box.createVerticalGlue());
        startingLevelSpinner = new SettingSpinner("Starting level: ", 
                                                  Hop.gameRulesC.getStartingLevel(), 
                                                  1,
                                                  Hop.gameRulesC.getMaxLevel(),
                                                  1);
        add(startingLevelSpinner);
        
        add(Box.createVerticalGlue());
        maxNumJumpsSpinner = new SettingSpinner("Max number of jumps: ", 
                                                 Hop.axelC.getMaxNumJumps(), 
                                                 1,
                                                 5,
                                                 1);
        add(maxNumJumpsSpinner);

        add(Box.createVerticalGlue());
        altitudeGapSpinner = new SettingSpinner("Altitude gap between blocks : ", 
                                                Hop.blockC.getAltitudeGap(), 
                                                10,
                                                Hop.windowC.getHeight() / 2,
                                                10);
        add(altitudeGapSpinner);

        add(Box.createVerticalGlue());
        enableFireBalls = new SettingBoolean("Enable fireballs: ", 
                                              Hop.gameRulesC.isFireBallsEnabled());
        add(enableFireBalls);


        add(Box.createVerticalGlue());
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setPreferredSize(new Dimension(Hop.windowC.getWidth() - 200, 120));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setPreferredSize(new Dimension(120, 32));
        backButton.addActionListener(e -> triggerBack());
        buttonPanel.add(backButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 24));
        saveButton.setPreferredSize(new Dimension(120, 32));
        saveButton.addActionListener(e -> triggerSave());
        buttonPanel.add(saveButton);
     
        add(buttonPanel);
    }
}
