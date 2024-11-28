import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class Field {
    public static final int ALTITUDE_GAP = 80;// gap entre bloc
    public static final int START_ALTITUDE = 40; 

    public final int width, height;
    private int bottom, top; // bottom and top altitude
    private ArrayList<Block> Blocks;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.Blocks = new ArrayList<Block>();
        
        int altitude = START_ALTITUDE;
        while (altitude < height) {
            int x = randNum(0, width);
            int blockWidth = randNum(50, 80);
            if (altitude == START_ALTITUDE) {
                x = (blockWidth + width)/2;
            }
            Blocks.add(new Block(x, altitude, blockWidth));
            altitude += ALTITUDE_GAP;
        }
    }

    public static int randNum(int a, int b) { //[a, b] inclus
        return ThreadLocalRandom.current().nextInt(a, b + 1);
    }

    public ArrayList<Block> getBlocks() {
        return Blocks;
    }

    private int x, y;

    private boolean falling; // 
    private boolean jumping; // set true when up; up speed = max, falli
    public void update() { }
}
