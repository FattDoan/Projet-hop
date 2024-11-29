public class Axel {
    public static final double MAX_FALL_SPEED = -800; // pixels/s
    public static final double JUMP_SPEED = 800; // pixels/s
    public static final double GRAVITY = 25; // pixels/s
    public static final double DIVE_SPEED = 3 * GRAVITY; // pixels/s   
    public static final double LATERAL_SPEED = 300; // pixels/s


    private int x, y;
 	private double xSpeed, ySpeed;

    private boolean jumping;
    private boolean diving;
	private boolean inAir;
	private int numJumps; // allow double jumps
    private boolean left; // go left
    private boolean right;// go right

    private boolean surviving;

    private final Field field;

    public Axel(Field f, int x, int y) {
        this.field = f;
        this.x = x;
        this.y = y;
		this.xSpeed = this.ySpeed = 0;
		this.inAir = false;
        this.surviving = true;
    }

    public int getX() { return x; }
    public int getY() { return y; }
	public void setJumping(boolean val) { jumping = val; }
	public void setDiving(boolean val) 	{ diving = val; }
	public void setLeft(boolean val) 	{ left = val; }
	public void setRight(boolean val) 	{ right = val; }

	public void computeMove() {
		if (left == false && right == false) {
			xSpeed = 0;
		}
		if (left == true) {
			xSpeed = LATERAL_SPEED;
		}
		if (right == true) {
			xSpeed = -LATERAL_SPEED;
		}
		if (jumping == true) {
			ySpeed += JUMP_SPEED;
			jumping = false;
		}
		if (diving == true) {
			ySpeed -= DIVE_SPEED;
			// this will keep diving until the player releases the key
		}
	}
    public void update() { 
		computeMove();
		ySpeed -= GRAVITY;
		ySpeed = Math.max(ySpeed, MAX_FALL_SPEED);
		x += xSpeed * ((double)Hop.DELAY/1000);
		y += ySpeed * ((double)Hop.DELAY/1000);
		
		x = Math.max(x, 0);
		x = Math.min(x, field.width);
		y = Math.max(y, 0);
		if (y == 0) { //TODO: not inAir
			ySpeed = 0;
		}
	}
}
