import java.util.LinkedList;
import java.util.Iterator;

public class Field {
    public static final int ALTITUDE_GAP = 80;// gap entre bloc
    public static final int START_ALTITUDE = 50; 
	public static final int[] MIN_BLOCKWIDTH = { 50, 45, 40, 35, 30, 25, 20, 15};
	public static final int[] MAX_BLOCKWIDTH = {100, 90, 80, 70, 60, 50, 40, 30};
	public static final int[] NUM_FIREBALLS  = {  1,  1,  2,  2,  3,  3,  4,  4};
	public static final int[] FALLING_SPEED =  {  1,  1,  1,  2,  2,  2,  3,  3};

	GameConfig.WindowConfig window = ConfigManager.getInstance().getConfig().window;

	public final int width, height;
    private LinkedList<Block> Blocks; // operations addFirst, addLast, removeFirst 
									  // much faster than ArrayLIst

	private LinkedList<FireBall> FireBalls;	// frequent deletions much faster than ArrayList

	private int passedAltitude;

    public Field(int width, int height, int level) {
        this.width = width;
        this.height = height;
        this.Blocks = new LinkedList<Block>();
    	this.FireBalls = new LinkedList<FireBall>();
		this.passedAltitude = 0; 
       	int blockWidth = Utils.randNum(MIN_BLOCKWIDTH[level], MAX_BLOCKWIDTH[level]);
		int x = (window.getWidth() - blockWidth)/2; 
		Blocks.addLast(new Block(x, START_ALTITUDE, blockWidth));
		for (int i = START_ALTITUDE + ALTITUDE_GAP; i < this.height; i += ALTITUDE_GAP) {
			addNewBlock(level);
		}
		for (int i = 0; i < NUM_FIREBALLS[level]; i++) {
			addFireBall(level);
		}
    }
	public int getPassedAltitude() { return passedAltitude; }
    public LinkedList<Block> getBlocks() {
        return Blocks;
    }
	public LinkedList<FireBall> getFireBalls() {
		return FireBalls;
	}
	public void addNewBlock(int level) {
		Block lastBlock = Blocks.getLast();
		int y = lastBlock.getY() + ALTITUDE_GAP;
		int blockWidth = Utils.randNum(MIN_BLOCKWIDTH[level], MAX_BLOCKWIDTH[level]);
		int x = Utils.randNum(0, width - blockWidth);
		Blocks.addLast(new Block(x, y, blockWidth));
	}
	public void addFireBall(int level) {
		boolean flag = false;
		while (!flag) {
			int x = Utils.randNum(0, width);
			flag = true;
			for (FireBall fb: FireBalls) {
				if (Math.abs(fb.getX() - x) < 4 * FireBall.RADIUS) {
					flag = false;
					break;
				}
			}
		}
		FireBalls.addLast(new FireBall(Utils.randNum(0, width), -2*FireBall.RADIUS));	
	}
	public void updateBlocks(int level) {
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
	public void updateFireBalls(int level) {
		Iterator<FireBall> iterator = FireBalls.iterator();   // this or using 2 for loops
															  // if using 1 loop for iterating and removing ele simultanously
															  // can cause ConcurrentModificationException
		while (iterator.hasNext()) {
    		FireBall fb = iterator.next();
    		fb.update();
    		if (fb.isDisappeared()) {
        		iterator.remove();
    		}
		}
		
		while (FireBalls.size() < NUM_FIREBALLS[level]) {
			addFireBall(level);
		}
	
	}
    public void update(int level) {
		passedAltitude += FALLING_SPEED[level];
		updateBlocks(level);
		updateFireBalls(level);
	}
}
