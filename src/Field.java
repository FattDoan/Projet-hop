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
            int blockWidth = Utils.randNum(45, 90);
            int x = Utils.randNum(0, width - blockWidth);
            if (altitude == START_ALTITUDE) {
                x = (blockWidth + width)/2;
            }
            Blocks.add(new Block(x, altitude, blockWidth));
            altitude += ALTITUDE_GAP;
        }
    }

    public ArrayList<Block> getBlocks() {
        return Blocks;
    }

    private int x, y; // WTF is this for?

    private boolean falling; // 
    private boolean jumping; // set true when up; up speed = max, falli
    public void update() { }
}
