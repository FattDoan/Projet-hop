public class Axel {
    public static final double MAX_FALL_SPEED = -600; // pixels/s
    public static final double JUMP_SPEED = 800; // pixels/s
    public static final double GRAVITY = 25; // pixels/s
    public static final double DIVE_SPEED = 3 * GRAVITY; // pixels/s   
    public static final double LATERAL_SPEED = 300; // pixels/s
    public static final double MAX_JUMP_TIMES = 2; // allow double jumps

    private GameConfig.gameRulesConfig gameRules = ConfigManager.getInstance().getConfig().gameRules;
    private int DELAY = (int) 1000 / gameRules.getFps();

    private int x, y;
    private double xSpeed, ySpeed;

    private boolean jumping;
    private boolean diving;
    private boolean onBlock;
    private int numJumps; // allow double jumps
    private boolean left; // go left
    private boolean right;// go right

    private boolean surviving;

    private final Field field;

    public Axel(Field f, int x, int y) {
        this.field = f;
        this.x = x;
        this.y = y;
        this.numJumps = 2;
        this.xSpeed = this.ySpeed = 0;
        this.onBlock = true;
        this.surviving = true;
    }

    public int getX()                   { return x; }
    public int getY()                   { return y; }
    public boolean isOnBlock()          { return onBlock; }
    public boolean isSurviving()        { return surviving; }
    public void setJumping(boolean val) { jumping = val; }
    public void setDiving(boolean val)  { diving = val; }
    public void setLeft(boolean val)    { left = val; }
    public void setRight(boolean val)   { right = val; }

    public void computeMove() {
        if (left == false && right == false) {
            xSpeed = 0;
        }
        if (left == true) {
            xSpeed = -LATERAL_SPEED;
        }
        if (right == true) {
            xSpeed = LATERAL_SPEED;
        }
        if (jumping == true) {
            if (onBlock == true) numJumps = 0;
            if (numJumps < MAX_JUMP_TIMES) {
                if (ySpeed < 0) ySpeed += JUMP_SPEED;
                else ySpeed = JUMP_SPEED;
                numJumps++;
                jumping = false;
            }
        }
        if (diving == true) {
            ySpeed -= DIVE_SPEED;
            // this will keep diving until the player releases the key
        }
    }
    public void checkCollision() {
        if (ySpeed < 0) {
            for (Block b: field.getBlocks()) {
                if ( Math.abs(this.y - b.getY()) <= GamePanel.getBlockHeight()/2 && 
                        b.getX() <= this.x && this.x <= b.getX() + b.getWidth()) {
                    ySpeed = 0;
                    y = b.getY();
                    onBlock = true;
                    break;
                        }
            }
            for (FireBall fb: field.getFireBalls()) {
                if ( Math.abs(this.y + FireBall.RADIUS - fb.getY()) <= FireBall.RADIUS && 
                     Math.abs(this.x - fb.getX()) <= FireBall.RADIUS) {
                    surviving = false;
                    break;
                        }
            }
        }
        else onBlock = false;
    }
    public void update() { 
        computeMove();
        ySpeed -= GRAVITY;
        ySpeed = Math.max(ySpeed, MAX_FALL_SPEED);
        x += xSpeed * ((double)DELAY/1000);
        y += ySpeed * ((double)DELAY/1000);

        x = Math.max(x, 0);
        x = Math.min(x, field.width);

        checkCollision();
        if (y < 0) {
            surviving = false;
        }
    }
}
