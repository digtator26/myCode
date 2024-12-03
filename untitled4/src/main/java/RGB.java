public class RGB {

    public static final RGB White = new RGB(255, 255, 255);
    public static final RGB Gray = new RGB(128, 128, 128);
    public static final RGB Black = new RGB(0, 0, 0);

    private int R;
    private int G;
    private int B;

    public RGB(int r, int g, int b) {
        R = r;
        G = g;
        B = b;
    }

    public int getR() {
        return R;
    }

    public int getG() {
        return G;
    }

    public int getB() {
        return B;
    }

    public boolean isWhite() {
        return R == 255 && G == 255 && B == 255;
    }

    public boolean isGray() {
        return R == 128 && G == 128 && B == 128;
    }

    public boolean isBlack() {
        return R == 0 && G == 0 && B == 0;
    }

    @Override
    public String toString() {
        return "RGB{" +
                "R=" + R +
                ", G=" + G +
                ", B=" + B +
                '}';
    }
}
