import java.util.LinkedList;
import java.util.Iterator;

public class Field { 
    public final int width, height;
    private LinkedList<Block> Blocks; // operations addFirst, addLast, removeFirst 
                                      // much faster than ArrayLIst

    private LinkedList<FireBall> FireBalls; // frequent deletions much faster than ArrayList

    /* passedAltitude is the highest altitude of the block that Axel is currently on */
    /* it is used to calculate the score */
    private int passedAltitude;

    public Field(int width, int height, int level) {
        this.width = width;
        this.height = height;
        this.Blocks = new LinkedList<Block>();
        this.FireBalls = new LinkedList<FireBall>();
        this.passedAltitude = 0; 
        /* add the first block -> this block is centered at the starting altitude */
        int blockCWidth = Utils.randNum(Utils.at(Hop.gameRulesC.getMinBlockWidthAtLevel(),level), 
                                        Utils.at(Hop.gameRulesC.getMaxBlockWidthAtLevel(),level));
        int x = (Hop.windowC.getWidth() - blockCWidth)/2; 
        Blocks.addLast(new Block(x, Hop.blockC.getStartAltitude(), blockCWidth));

        /* add new blocks until window height is reached */
        for (int i = Hop.blockC.getStartAltitude() + Hop.blockC.getAltitudeGap(); i < this.height; i += Hop.blockC.getAltitudeGap()) {
            addNewBlock(level);
        }

        /* add fireballs (if enabled) */
        if (Hop.gameRulesC.isFireBallsEnabled()) {
            for (int i = 0; i < Utils.at(Hop.gameRulesC.getNumFireBallsAtLevel(),level); i++) {
                addFireBall(level);
            }
        }
    }
    
    /* getters */
    public LinkedList<Block> getBlocks() {
        return Blocks;
    }
    public LinkedList<FireBall> getFireBalls() {
        return FireBalls;
    }

    /* getter but also need to make sure it's higher than the height need to reach at current level */
    public int getPassedAltitude(int level) { 
        if (level > 1) {
            passedAltitude = Math.max(passedAltitude, Utils.at(Hop.gameRulesC.getHeightToReachNextLevel(),level - 1));
        }
        return passedAltitude;
    }
    public void addNewBlock(int level) {
        Block lastBlock = Blocks.getLast();
        int y = lastBlock.getY() + Hop.blockC.getAltitudeGap();
        int blockCWidth = Utils.randNum(Utils.at(Hop.gameRulesC.getMinBlockWidthAtLevel(),level),
                                        Utils.at(Hop.gameRulesC.getMaxBlockWidthAtLevel(),level));
        int x = Utils.randNum(0, width - blockCWidth);
        Blocks.addLast(new Block(x, y, blockCWidth));
    }
    public void addFireBall(int level) {
        boolean flag = false;
        while (!flag) {
            int x = Utils.randNum(0, width);
            flag = true;
            for (FireBall fb: FireBalls) {
                if (Math.abs(fb.getX() - x) < 4 * Hop.fireBallC.getRadius()) {
                    flag = false;
                    break;
                }
            }
        }
        FireBalls.addLast(new FireBall(Utils.randNum(0, width), -2*Hop.fireBallC.getRadius()));   
    }

    public void updateBlocks(int level) {
        /* at each frame, decrease the altitude of each block by a fixed amount (according to gameRules) */
        for (Block b: Blocks) {
            b.setY(b.getY() - Utils.at(Hop.gameRulesC.getBlockFallSpeedPixelsPerFrameAtLevel(),level));
        }
        /* if a block is out of the window, remove it */
        if (Blocks.getFirst().getY() < 0) {
            Blocks.removeFirst();
        }
        /* if the height of the last block is less than a fixed amount, add a new block */
        if (this.height - Blocks.getLast().getY() >= Hop.blockC.getAltitudeGap()) {
            addNewBlock(level);
        }
    }
    public void updateFireBalls(int level) {
        // using iterator or 2 for loops to avoid ConcurrentModificationException
        // i.e removing an element while still iterating over the list
        Iterator<FireBall> iterator = FireBalls.iterator();   
        // if the firebball has disappeared (i.e. out of the window whereas its y < 0), remove it
        while (iterator.hasNext()) {
            FireBall fb = iterator.next();
            fb.update();
            if (fb.isDisappeared()) {
                iterator.remove();
            }
        }
        // while the number of fireballs is less than a fixed amount (according to gameRules), add a new fireball
        while (FireBalls.size() < Utils.at(Hop.gameRulesC.getNumFireBallsAtLevel(),level)) {
            addFireBall(level);
        }

    }
    /* update the field at each frame */
    public void update(int level) {
        passedAltitude += Utils.at(Hop.gameRulesC.getBlockFallSpeedPixelsPerFrameAtLevel(),level);
        updateBlocks(level);
        if (Hop.gameRulesC.isFireBallsEnabled()) {
            updateFireBalls(level);
        }
    }
}
