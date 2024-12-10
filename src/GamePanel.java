import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {
    private GameConfig gameConfig = ConfigManager.getInstance().getConfig();

    private GameConfig.AxelConfig axelC = gameConfig.axel;
    private GameConfig.FireBallConfig fireBallC = gameConfig.fireBall;
    private GameConfig.BlockConfig blockC = gameConfig.block;

    private Image blockTexture;
    private Image backgroundTexture;
    private final Axel axel;
    private final Field field;

    public GamePanel(Field field, Axel axel) {
        this.field = field;
        this.axel = axel;

        setPreferredSize(new Dimension(field.width, field.height));
        //      blockTexture = new ImageIcon("../assets/block.png").getImage();
        //      backgroundTexture = new ImageIcon("../assets/background.png").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        // Enable anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Continue with default painting
        super.paintComponent(g2);

        // <!> ATTENTION:  Coordinate system in JPanel is different from Field
        // i.e In JPanel, (0,0) is the top-left corner 
        //     while in Field, (0,0) is the bottom-left
        // We need to convert the coordinates ONLY in paintComponent (rendering the graphics)
        // The actual calculation of game physics should only be done in Field coordinates system

        /*for (int x = 0; x < field.width; x += 16) {
          for (int y = 0; y < field.height; y += 16) {
          g2.drawImage(backgroundTexture, x, y, Math.min(x + 16, field.width), Math.min(y + 16, field.height),
          0, 0, 16, 16, null);
          }
          }*/
        //g2.fillRect(0, 0, field.width, field.height);

        for (Block b: field.getBlocks()) {
            g2.fillRect(b.getX(), field.height - b.getY(), b.getWidth(), blockC.getHeight());
            /*int blockX = b.getX();
              int blockY = field.height - b.getY();
              int blockWidth = b.getWidth();
              int blockHeight = BLOCK_HEIGHT;
              for (int x = blockX; x < blockX + blockWidth; x += 16) {    
              g2.drawImage(blockTexture, x, blockY, Math.min(x + 16, blockX + blockWidth), blockY + blockHeight,
              0, 0, 16, 16, null);
              }*/
        }
        g2.fillOval(axel.getX() - axelC.getWidth()/2, (field.height - axel.getY()) - axelC.getHeight(), axelC.getWidth(), axelC.getHeight());
        g2.setColor(Color.RED);
        for (FireBall fb: field.getFireBalls()) {
            g2.fillOval(fb.getX() - fireBallC.getRadius(), field.height - fb.getY() - 2*fireBallC.getRadius(), 2*fireBallC.getRadius(), 2*fireBallC.getRadius());
        }
    }
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                axel.setLeft(true);
                axel.setRight(false);
                break;

            case KeyEvent.VK_RIGHT:
                axel.setRight(true);
                axel.setLeft(false);
                break;

            case KeyEvent.VK_UP:
                axel.setJumping(true);
                break;

            case KeyEvent.VK_DOWN:
                axel.setDiving(true);
                break;

        } 
    }
    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                axel.setLeft(false);
                break;

            case KeyEvent.VK_RIGHT:
                axel.setRight(false);
                break;

            case KeyEvent.VK_UP:
                axel.setJumping(false);
                break;

            case KeyEvent.VK_DOWN:
                axel.setDiving(false);
                break;

        } 
    }
}
