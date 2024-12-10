public class Block {
    private final int x; // position horizontale / altitude
    private int y;
    private final int width; // largeur

    public Block(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    public int getX()           { return x; }
    public int getY()           { return y; }
    public void setY(int newY)  { this.y = newY; }
    public int getWidth()       { return width; }
}
