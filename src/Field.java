import java.util.LinkedList;

public class Field {
    public static final int ALTITUDE_GAP = 100;// gap entre bloc
    public static final int START_ALTITUDE = 50; 
	public static final int[] MIN_BLOCKWIDTH = { 50, 45, 40, 35, 30, 25, 20, 15};
	public static final int[] MAX_BLOCKWIDTH = {100, 90, 80, 70, 60, 50, 40, 30};
	public static final int[] FALLING_SPEED =  {  1,  1,  1,  1,  2,  2,  2, 3};

	public final int width, height;
    private LinkedList<Block> Blocks; // operations addFirst, addLast, removeFirst 
									  // much faster than ArrayLIst


	private int passedAltitude;

    public Field(int width, int height, int level) {
        this.width = width;
        this.height = height;
        this.Blocks = new LinkedList<Block>();
    	this.passedAltitude = 0;    
       	int blockWidth = Utils.randNum(MIN_BLOCKWIDTH[level], MAX_BLOCKWIDTH[level]);
		int x = (Hop.WIDTH - blockWidth)/2; 
		Blocks.addLast(new Block(x, START_ALTITUDE, blockWidth));
		for (int i = START_ALTITUDE + ALTITUDE_GAP; i < this.height; i += ALTITUDE_GAP) {
			addNewBlock(level);
		}
    }
	
	public int getPassedAltitude() { return passedAltitude; }
    public LinkedList<Block> getBlocks() {
        return Blocks;
    }

	public void addNewBlock(int level) {
		Block lastBlock = Blocks.getLast();
		int y = lastBlock.getY() + ALTITUDE_GAP;
		int blockWidth = Utils.randNum(MIN_BLOCKWIDTH[level], MAX_BLOCKWIDTH[level]);
		int x = Utils.randNum(0, width - blockWidth);
		Blocks.addLast(new Block(x, y, blockWidth));
	}
    public void update(int level) {
		passedAltitude += FALLING_SPEED[level];
		for (Block b: Blocks) {
			b.setY(b.getY() - FALLING_SPEED[level]);
		}
		if (Blocks.getFirst().getY() < 0) {
			Blocks.removeFirst();
		}
		if (this.height - Blocks.getLast().getY() >= ALTITUDE_GAP) {
			addNewBlock(level);
		}
	}
}
