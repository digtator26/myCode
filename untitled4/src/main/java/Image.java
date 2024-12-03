import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public class Image {

    private final BufferedImage bufferedImage;
    private List<Point> points;
    private List<Path> paths;

    public Image(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                if (getColor(i, j).isGray()) {
                    setColor(i, j, RGB.White);
                }
            }
        }
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public RGB getColor(int x, int y) {
        WritableRaster writableRaster = bufferedImage.getRaster();
        int[] pixel = writableRaster.getPixel(x, y, new int[4]);
        return new RGB(pixel[0], pixel[1], pixel[2]);
    }

    public void setColor(int x, int y, RGB rgb) {
        WritableRaster writableRaster = bufferedImage.getRaster();
        int[] pixel = writableRaster.getPixel(x, y, new int[4]);
        pixel[0] = rgb.getR();
        pixel[1] = rgb.getG();
        pixel[2] = rgb.getB();
        writableRaster.setPixel(x, y, pixel);
        bufferedImage.setData(writableRaster);
    }

    public Image draw() {
        Paint.doIt(bufferedImage);
        return this;
    }

    public Image clear() {
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                if (!getColor(i, j).isBlack()) {
                    setColor(i, j, RGB.White);
                }
            }
        }
        return this;
    }

    public Image skeletonization() {
        for (int i = 1; i < bufferedImage.getWidth() - 1; i++) {
            for (int j = 1; j < bufferedImage.getHeight() - 1; j++) {
                for (int iter = 0; iter < 2; iter++) {
                    int p2 = getColor(i, j - 1).getB() / 255 == 1 ? 0 : 1;
                    int p3 = getColor(i + 1, j - 1).getB() / 255 == 1 ? 0 : 1;
                    int p4 = getColor(i + 1, j).getB() / 255 == 1 ? 0 : 1;
                    int p5 = getColor(i + 1, j + 1).getB() / 255 == 1 ? 0 : 1;
                    int p6 = getColor(i, j + 1).getB() / 255 == 1 ? 0 : 1;
                    int p7 = getColor(i - 1, j + 1).getB() / 255 == 1 ? 0 : 1;
                    int p8 = getColor(i - 1, j).getB() / 255 == 1 ? 0 : 1;
                    int p9 = getColor(i - 1, j - 1).getB() / 255 == 1 ? 0 : 1;

                    int A = ((p2 == 0 && p3 == 1) ? 1 : 0) + ((p3 == 0 && p4 == 1) ? 1 : 0) +
                            ((p4 == 0 && p5 == 1) ? 1 : 0) + ((p5 == 0 && p6 == 1) ? 1 : 0) +
                            ((p6 == 0 && p7 == 1) ? 1 : 0) + ((p7 == 0 && p8 == 1) ? 1 : 0) +
                            ((p8 == 0 && p9 == 1) ? 1 : 0) + ((p9 == 0 && p2 == 1) ? 1 : 0);
                    int B = p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
                    int m1 = iter == 0 ? (p2 * p4 * p6) : (p2 * p4 * p8);
                    int m2 = iter == 0 ? (p4 * p6 * p8) : (p2 * p6 * p8);

                    if (A == 1 && (B >= 2 && B <= 6) && m1 == 0 && m2 == 0) {
                        setColor(i, j, RGB.White);
                    }
                }
            }
        }
        return this;
    }

    public Image selectionOfControlPoints() {
        points = new ArrayList<>();
        paths = new ArrayList<>();

        boolean[][] check = new boolean[getWidth()][getHeight()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                check[i][j] = getColor(i, j).isWhite();
            }
        }

        for (int i = 1; i < getWidth() - 1; i++) {
            for (int j = 1; j < getHeight() - 1; j++) {
                if (!check[i][j]) {
                    List<Point> neighbours = getNeighbours(i, j);
                    if (neighbours.size() != 1) {
                        if (neighbours.size() == 2 || !findCross(neighbours, i, j)) {
                            continue;
                        }
                    }
                    // работа со стартовой
                    Point s = new Point(i, j, getColor(i, j));

                    Point tek = s;
                    List<Point> path = new ArrayList<>();
                    while (true) {
                        neighbours = getNeighbours(tek.getX(), tek.getY());
                        for (int k = 0; k < neighbours.size(); k++) {
                            if (check[neighbours.get(k).getX()][neighbours.get(k).getY()]) {
                                neighbours.remove(k);
                                k--;
                            }
                        }
                        check[tek.getX()][tek.getY()] = true;
                        if (neighbours.size() == 0) {
                            //1
                            boolean neighbourIsCheckPoint = false;
                            for (Point neighbour: neighbours) {
                                neighbourIsCheckPoint = points.contains(neighbour);
                                if (neighbourIsCheckPoint) {
                                    break;
                                }
                            }
                            if (neighbourIsCheckPoint) {

                            }

                        }

                    }
                }
            }
        }
        for (int index = 0; index < points.size(); index++) {
            List<Point> neighbours = getNeighbours(points.get(index).getX(), points.get(index).getY());
            for (Point neighbour: neighbours) {
                if (!check[neighbour.getX()][neighbour.getY()]) {
                    // работа со стартовой

                }
            }
        }

        return this;
    }

    private List<Point> getNeighbours(int i, int j) {
        List<Point> neighbours = new ArrayList<>();
        if (getColor(i, j - 1).isBlack()) {
            neighbours.add(new Point(i, j - 1, getColor(i, j - 1)));
        }
        if (getColor(i + 1, j - 1).isBlack()) {
            neighbours.add(new Point(i + 1, j - 1, getColor(i + 1, j - 1)));
        }
        if (getColor(i + 1, j).isBlack()) {
            neighbours.add(new Point(i + 1, j, getColor(i + 1, j)));
        }
        if (getColor(i + 1, j + 1).isBlack()) {
            neighbours.add(new Point(i + 1, j + 1, getColor(i + 1, j + 1)));
        }
        if (getColor(i, j + 1).isBlack()) {
            neighbours.add(new Point(i, j + 1, getColor(i, j + 1)));
        }
        if (getColor(i - 1, j + 1).isBlack()) {
            neighbours.add(new Point(i - 1, j + 1, getColor(i - 1, j + 1)));
        }
        if (getColor(i - 1, j).isBlack()) {
            neighbours.add(new Point(i - 1, j, getColor(i - 1, j)));
        }
        if (getColor(i - 1, j - 1).isBlack()) {
            neighbours.add(new Point(i - 1, j - 1, getColor(i - 1, j - 1)));
        }
        return neighbours;
    }

    private boolean findCross(List<Point> neighbours, int x, int y) {
        int count = 0;
        for (int i = 0; i < neighbours.size(); i++) {
            for (int j = 0; j < neighbours.size(); j++) {
                if (i != j) {
                    boolean condition1 = false;
                    boolean condition2 = false;
                    if (neighbours.get(i).getX() != neighbours.get(j).getX()
                        && neighbours.get(i).getY() != neighbours.get(j).getY()) {
                        condition1 = true;
                    }
                    if (neighbours.get(i).getX() == neighbours.get(j).getX() && neighbours.get(i).getX() == x
                        || neighbours.get(i).getY() == neighbours.get(j).getY() && neighbours.get(i).getY() == y) {
                        condition2 = true;
                    }
                    if (condition1 || condition2) {
                        count++;
                        if (count == 2) {
                            return true;
                        }
                        for (int k = 0; k < neighbours.size() ; k++) {
                            if (k != i && k != j && (neighbours.get(k).getX() == neighbours.get(i).getX()
                                || neighbours.get(k).getX() == neighbours.get(j).getX()
                                    || neighbours.get(k).getY() == neighbours.get(i).getY()
                                    || neighbours.get(k).getY() == neighbours.get(j).getY())) {
                                neighbours.remove(k);
                                if (k < i) {
                                    i--;
                                }
                                if (k < j) {
                                    j--;
                                }
                                k--;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public List<Segment> segmentation() {
        List<Segment> segments = new ArrayList<>();
        for (int shift = 0; shift + 40 <= getWidth(); shift+=40) {
            int top = 0;
            for (int j = 0; j < getHeight(); j++) {
                for (int i = shift; i < shift + 40; i++) {
                    if (getColor(i, j).isBlack()) {
                        top = j;
                        j = getHeight();
                        break;
                    }
                }
            }

            int down = 0;
            for (int j = getHeight() - 1; j >= 0; j--) {
                for (int i = shift; i < shift + 40; i++) {
                    if (getColor(i, j).isBlack()) {
                        down = j;
                        j = -1;
                        break;
                    }
                }
            }

            int left = 0;
            for (int i = shift; i < shift + 40; i++) {
                for (int j = 0; j < getHeight(); j++) {
                    if (getColor(i, j).isBlack()) {
                        left = i;
                        i = shift + 40;
                        break;
                    }
                }
            }

            int right = 0;
            for (int i = shift + 40 - 1; i >= shift; i--) {
                for (int j = 0; j < getHeight(); j++) {
                    if (getColor(i, j).isBlack()) {
                        right = i;
                        i = shift - 1;
                        break;
                    }
                }
            }

            List<Integer> xs = new ArrayList<>();
            for (int j = top; j <= down; j++) {
                int count = 0;
                for (int i = left; i <= right; i++) {
                    if (getColor(i, j).isBlack()) {
                        count++;
                    }
                }
                xs.add(count);
            }

            List<Integer> ys = new ArrayList<>();
            for (int i = left; i <= right; i++) {
                int count = 0;
                for (int j = top; j <= down; j++) {
                    if (getColor(i, j).isBlack()) {
                        count++;
                    }
                }
                ys.add(count);
            }

            segments.add(new Segment(xs, ys));
        }
        return segments;
    }

}
