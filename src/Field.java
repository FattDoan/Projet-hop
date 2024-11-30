import java.util.LinkedList;

public class Field {
    public static final int ALTITUDE_GAP = 80;// gap entre bloc
    public static final int START_ALTITUDE = 40; 

    public final int width, height;
    private int bottom, top; // bottom and top altitude
    private LinkedList<Block> Blocks; // operations addFirst, addLast, removeFirst 
									  // much faster than ArrayLIst

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.Blocks = new LinkedList<Block>();
        
       	int blockWidth = Utils.randNum(45, 90);
		int x = (Hop.WIDTH - blockWidth)/2; 
		Blocks.addLast(new Block(x, START_ALTITUDE, blockWidth));
		for (int i = START_ALTITUDE + ALTITUDE_GAP; i < this.height; i += ALTITUDE_GAP) {
			addNewBlock();
		}
    }

    public LinkedList<Block> getBlocks() {
        return Blocks;
    }

	public void addNewBlock() {
		Block lastBlock = Blocks.getLast();
		int y = lastBlock.getY() + ALTITUDE_GAP;
		int blockWidth = Utils.randNum(45, 90);
		int x = Utils.randNum(0, width - blockWidth);
		Blocks.addLast(new Block(x, y, blockWidth));
	}
    private int x, y; // WTF is this for?

    private boolean falling; // 
    private boolean jumping; // set true when up; up speed = max, falli
    public void update() {
		for (Block b: Blocks) {
			b.setY(b.getY() - 1);
		}
		if (Blocks.getFirst().getY() < 0) {
			Blocks.removeFirst();
		}
		if (this.height - Blocks.getLast().getY() >= ALTITUDE_GAP) {
			addNewBlock();
		}
	}
}
