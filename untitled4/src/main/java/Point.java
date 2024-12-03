public class Point {

    private final int x;
    private final int y;
    private final RGB rgb;

    public Point(int x, int y, RGB rgb) {
        this.x = x;
        this.y = y;
        this.rgb = rgb;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public RGB getRgb() {
        return rgb;
    }
}
