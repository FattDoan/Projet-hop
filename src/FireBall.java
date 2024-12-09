public class FireBall {
	private	int x, y;
	private int ySpeed;
	private boolean disappeared = false;

	public static final int RADIUS = 8;
	public static final int GRAVITY = 7;
	public static final int MAX_FALL_SPEED = -600;

	private	GameConfig.gameRulesConfig gameRules = ConfigManager.getInstance().getConfig().gameRules;
	private int DELAY = (int) 1000 / gameRules.getFps();


	public FireBall(int x, int y, int ySpeed) {
		this.x = x;
		this.y = y;
		this.ySpeed = ySpeed;
	}
	public FireBall(int x, int y) {
		this(x, y, Utils.randNum(600, 900));
	}
	public int getX() 			{ return x; }
	public int getY() 			{ return y; }
	public int getYSpeed()		{ return ySpeed; }
	public boolean isDisappeared() { return disappeared; }
	public void setY(int newY) 	{ this.y = newY; }


	public void update() {
		ySpeed -= GRAVITY;
		ySpeed = Math.max(ySpeed, MAX_FALL_SPEED);
		y += ySpeed * ((double)DELAY/1000);
		if (y < -5*RADIUS) {
			disappeared = true;
		}
	}
}
