public class FireBall {
    private int x, y;
    private int ySpeed;
    private boolean disappeared = false;

    public FireBall(int x, int y, int ySpeed) {
        this.x = x;
        this.y = y;
        this.ySpeed = ySpeed;
    }
    public FireBall(int x, int y) {
        this(x, y, Utils.randNum(600, 900));
    }
    public int getX()               { return x; }
    public int getY()               { return y; }
    public int getYSpeed()          { return ySpeed; }
    public boolean isDisappeared()  { return disappeared; }
    public void setY(int newY)      { this.y = newY; }

    public void update() {
        ySpeed -= Hop.fireBallC.getGravity();
        ySpeed = Math.max(ySpeed, Hop.fireBallC.getMaxFallSpeed());
        y += ySpeed * ((double)Hop.DELAY/1000);
        if (y < -5*Hop.fireBallC.getRadius()) {
            disappeared = true;
        }
    }
}
