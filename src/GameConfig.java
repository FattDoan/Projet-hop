public class GameConfig {
    public WindowConfig window;
    public GameRulesConfig gameRules;
    public AxelConfig axel;
    public FireBallConfig fireBall;
    public BlockConfig block;

    public static class WindowConfig {
        private int width;
        private int height;

        // Getters - boilerplate
        public int getWidth() {
            return width;
        }
        public int getHeight() {
            return height;
        }
    }

    public static class GameRulesConfig {
        private int fps;
        private int startingLevel;
        private int maxLevel;
        private boolean enableFireBalls;
        private int[] heightToReachNextLevel;
        private int[] minBlockWidthAtLevel;
        private int[] maxBlockWidthAtLevel;
        private int[] numFireBallsAtLevel;
        private int[] blockFallSpeedPixelsPerFrameAtLevel; 
        // Getters - boilerplate
        public int getFps() {
            return fps;
        }
        public int getStartingLevel() {
            return startingLevel;
        }
        public int getMaxLevel() {
            return maxLevel;
        }
        public boolean isFireBallsEnabled() {
            return enableFireBalls;
        }
        public int[] getHeightToReachNextLevel() {
            return heightToReachNextLevel;
        }
        public int[] getMinBlockWidthAtLevel() {
            return minBlockWidthAtLevel;
        }
        public int[] getMaxBlockWidthAtLevel() {
            return maxBlockWidthAtLevel;
        }
        public int[] getNumFireBallsAtLevel() {
            return numFireBallsAtLevel;
        }
        public int[] getBlockFallSpeedPixelsPerFrameAtLevel() {
            return blockFallSpeedPixelsPerFrameAtLevel;
        }
        public void setFireBallsEnabled(boolean val) {
            enableFireBalls = val;
        }
        public void setStartingLevel(int val) {
            startingLevel = val;
        }
    }
    public static class AxelConfig {
        private int width;
        private int height;
        private double maxFallSpeed; // pixels/s
        private double jumpSpeed; // pixels/s
        private double gravity; // pixels/s
        private double diveSpeed; // pixels/s   
        private double lateralSpeed; // pixels/s
        private int maxNumJumps; // allow double jumps

        // Getters - boilerplate
        public int getWidth() {
            return width;
        }
        public int getHeight() {
            return height;
        }
        public double getMaxFallSpeed() {
            return maxFallSpeed;
        }
        public double getJumpSpeed() {
            return jumpSpeed;
        }
        public double getGravity() {
            return gravity;
        }
        public double getDiveSpeed() {
            return diveSpeed;
        }
        public double getLateralSpeed() {
            return lateralSpeed;
        }
        public int getMaxNumJumps() {
            return maxNumJumps;
        }
        public void setMaxNumJumps(int val) {
            maxNumJumps = val;
        }
    }

    public static class FireBallConfig {
        private int radius;
        private int gravity;
        private int maxFallSpeed;

        // Getters - boilerplate
        public int getRadius() {
            return radius;
        }
        public int getGravity() {
            return gravity;
        }
        public int getMaxFallSpeed() {
            return maxFallSpeed;
        }
    }

    public static class BlockConfig {
        private int height;
        private int startAltitude;
        private int altitudeGap;

        // Getters - boilerplate
        public int getHeight() {
            return height;
        }
        public int getStartAltitude() {
            return startAltitude;
        }
        public int getAltitudeGap() {
            return altitudeGap;
        }
        public void setAltitudeGap(int val) {
            startAltitude = val;
        }
    }

}
