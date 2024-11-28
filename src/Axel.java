public class Axel {
    public static final double MAX_FALL_SPEED = -20;
    public static final double JUMP_SPEED = 20;
    public static final double GRAVITY = 1;
    public static final double DIVE_SPEED = 3 * GRAVITY;    
    public static final double LATERAL_SPEED = 8;
    // upspeed ?   

    private int x, y;

    private boolean falling; // 
    private boolean jumping; // set true when up; up speed = max, falling = true
    private boolean diving; //  
    private boolean left; // go left
    private boolean right;// go right

    private boolean surviving;

    private final Field field;

    public Axel(Field f, int x, int y) {
        this.field = f;
        this.x = x;
        this.y = y;
        this.surviving = true;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void update() { }
}