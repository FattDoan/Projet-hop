public class Axel {
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
            xSpeed = -Hop.axelC.getLateralSpeed();
        }
        if (right == true) {
            xSpeed = Hop.axelC.getLateralSpeed();
        }
        if (jumping == true) {
            if (onBlock == true) numJumps = 0;
            if (numJumps < Hop.axelC.getMaxNumJumps()) {
                if (ySpeed < 0) ySpeed += Hop.axelC.getJumpSpeed();
                else ySpeed = Hop.axelC.getJumpSpeed();
                numJumps++;
                jumping = false;
            }
        }
        if (diving == true) {
            ySpeed -= Hop.axelC.getDiveSpeed();
            // this will keep Axel continue diving until the player releases the key
        }
    }
    public void checkCollision() {
        // check for collision with blocks only when Axel is falling (i.e ySpeed < 0)
        if (ySpeed < 0) {
            for (Block b: field.getBlocks()) {
                if ( Math.abs(this.y - b.getY()) <= Hop.blockC.getHeight()/2 && 
                        b.getX() <= this.x && this.x <= b.getX() + b.getWidth()) {
                    ySpeed = 0;
                    y = b.getY();
                    onBlock = true;
                    break;
                        }
            }
        }
        else onBlock = false;

        // check for collision with fireballs at all times
        for (FireBall fb: field.getFireBalls()) {
            if ( Math.abs(this.y + Hop.fireBallC.getRadius() - fb.getY()) <= Hop.fireBallC.getRadius() && 
                 Math.abs(this.x - fb.getX()) <= Hop.fireBallC.getRadius()) {
                surviving = false;
                break;
            }
        }
    }
    public void update() { 
        computeMove();
        ySpeed -= Hop.axelC.getGravity();
        ySpeed = Math.max(ySpeed, Hop.axelC.getMaxFallSpeed());
        x += xSpeed * ((double)Hop.DELAY/1000);
        y += ySpeed * ((double)Hop.DELAY/1000);

        x = Math.max(x, 0);
        x = Math.min(x, field.width);

        checkCollision();
        if (y < 0) {
            surviving = false;
        }
    }
}
