import java.util.LinkedList;
import java.util.Iterator;

public class Field {
    
    public final int width, height;
    private LinkedList<Block> Blocks; // operations addFirst, addLast, removeFirst 
                                      // much faster than ArrayLIst

    private LinkedList<FireBall> FireBalls; // frequent deletions much faster than ArrayList

    private int passedAltitude;

    public Field(int width, int height, int level) {
        this.width = width;
        this.height = height;
        this.Blocks = new LinkedList<Block>();
        this.FireBalls = new LinkedList<FireBall>();
        this.passedAltitude = 0; 
        int blockCWidth = Utils.randNum(Utils.at(Hop.gameRulesC.getMinBlockWidthAtLevel(),level), 
                                        Utils.at(Hop.gameRulesC.getMaxBlockWidthAtLevel(),level));
        int x = (Hop.windowC.getWidth() - blockCWidth)/2; 
        Blocks.addLast(new Block(x, Hop.blockC.getStartAltitude(), blockCWidth));
        for (int i = Hop.blockC.getStartAltitude() + Hop.blockC.getAltitudeGap(); i < this.height; i += Hop.blockC.getAltitudeGap()) {
            addNewBlock(level);
        }
        if (Hop.gameRulesC.isFireBallsEnabled()) {
            for (int i = 0; i < Utils.at(Hop.gameRulesC.getNumFireBallsAtLevel(),level); i++) {
                addFireBall(level);
            }
        }
    }
    public int getPassedAltitude(int level) { 
        if (level > 1) {
            passedAltitude = Math.max(passedAltitude, Utils.at(Hop.gameRulesC.getHeightToReachNextLevel(),level - 1));
        }
        return passedAltitude;
    }
    public LinkedList<Block> getBlocks() {
        return Blocks;
    }
    public LinkedList<FireBall> getFireBalls() {
        return FireBalls;
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
        for (Block b: Blocks) {
            b.setY(b.getY() - Utils.at(Hop.gameRulesC.getBlockFallSpeedPixelsPerFrameAtLevel(),level));
        }
        if (Blocks.getFirst().getY() < 0) {
            Blocks.removeFirst();
        }
        if (this.height - Blocks.getLast().getY() >= Hop.blockC.getAltitudeGap()) {
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

        while (FireBalls.size() < Utils.at(Hop.gameRulesC.getNumFireBallsAtLevel(),level)) {
            addFireBall(level);
        }

    }
    public void update(int level) {
        passedAltitude += Utils.at(Hop.gameRulesC.getBlockFallSpeedPixelsPerFrameAtLevel(),level);
        updateBlocks(level);
        if (Hop.gameRulesC.isFireBallsEnabled()) {
            updateFireBalls(level);
        }
    }
}
