import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {
    private static final int BLOCK_HEIGHT = 10;
    private static final int AXEL_WIDTH = 10;
    private static final int AXEL_HEIGHT = 10;

    private final Axel axel;
    private final Field field;

    public GamePanel(Field field, Axel axel) {
        this.field = field;
        this.axel = axel;

        setPreferredSize(new Dimension(field.width, field.height));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // <!> ATTENTION:  Coordinate system in JPanel is different from Field
		// i.e In JPanel, (0,0) is the top-left corner 
		//     while in Field, (0,0) is the bottom-left
		// We need to convert the coordinates ONLY in paintComponent (rendering the graphics)
		// The actual calculation of game physics should only be done in Field coordinates system
        int W = field.width;
        int H = field.height;
		for (Block b: field.getBlocks()) {
            g.fillRect(W - b.getX(), H - b.getY(), b.getWidth(), BLOCK_HEIGHT);
        }
        g.fillOval((W - axel.getX()) - AXEL_WIDTH/2, (H - axel.getY()) - AXEL_HEIGHT, AXEL_WIDTH, AXEL_HEIGHT);
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
