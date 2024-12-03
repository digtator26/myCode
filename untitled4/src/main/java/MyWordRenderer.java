import net.logicsquad.nanocaptcha.image.renderer.AbstractWordRenderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class MyWordRenderer extends AbstractWordRenderer {

    private static final int SHIFT = 20;
    private static final int FONT_INDEX_SIZE = 100;
    private static final int[] INDEXES = new int[100];
    private static final AtomicInteger idxPointer = new AtomicInteger(0);
    private static final int FUDGE_MIN = -5;
    private static final int FUDGE_MAX = 5;
    private static final int FUDGE_INDEX_SIZE = 100;
    private static final int[] FUDGES = new int[100];
    private static final AtomicInteger fudgePointer = new AtomicInteger(0);
    private static final Color COLOR;
    private static final Font[] FONTS;

    public MyWordRenderer() {
        this(0.05D, 0.25D);
    }

    private MyWordRenderer(double xOffset, double yOffset) {
        super(xOffset, yOffset);
    }

    public void render(String word, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        int xBaseline = (int)((double)image.getWidth() * this.xOffset());
        int yBaseline = image.getHeight() - (int)((double)image.getHeight() * this.yOffset());
        char[] chars = new char[1];
        char[] var7 = word.toCharArray();
        int var8 = var7.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            char c = var7[var9];
            chars[0] = c;
            g.setColor(COLOR);
            g.setFont(this.nextFont());
            int xFudge = this.nextFudge();
            int yFudge = this.nextFudge();
            g.drawChars(chars, 0, 1, xBaseline, yBaseline - yFudge);
            xBaseline += 40;
        }

    }

    private Font nextFont() {
        return FONTS.length == 1 ? FONTS[0] : FONTS[INDEXES[idxPointer.getAndIncrement() % 100]];
    }

    private int nextFudge() {
        return FUDGES[fudgePointer.getAndIncrement() % 100];
    }

    static {
        COLOR = Color.BLACK;
        FONTS = new Font[2];
        FONTS[0] = (Font)DEFAULT_FONTS.get(0);
        FONTS[1] = (Font)DEFAULT_FONTS.get(1);

        int i;
        for(i = 0; i < 100; ++i) {
            INDEXES[i] = RAND.nextInt(FONTS.length);
        }

        for(i = 0; i < 100; ++i) {
            FUDGES[i] = RAND.nextInt(11) + -5;
        }

    }

    public static class Builder extends net.logicsquad.nanocaptcha.image.renderer.AbstractWordRenderer.Builder {
        public Builder() {
        }

        public MyWordRenderer build() {
            return new MyWordRenderer(this.xOffset, this.yOffset);
        }
    }

}
