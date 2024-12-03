import com.google.gson.Gson;
import net.logicsquad.nanocaptcha.image.ImageCaptcha;
import net.logicsquad.nanocaptcha.image.backgrounds.FlatColorBackgroundProducer;
import net.logicsquad.nanocaptcha.image.filter.RippleImageFilter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BaseSegments {

    public static final int number = 36 * 100;

    private List<Segment> segments;

    public BaseSegments() {
        Gson gson = new Gson();
        segments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("BaseSegments.txt"))) {
            for (int i = 0; i < number; i++) {
                segments.add(gson.fromJson(br.readLine(), Segment.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Segment get(int i) {
        return segments.get(i);
    }

    public int size() {
        return segments.size();
    }

    public static void generation() {
        Gson gson = new Gson();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("BaseSegments.txt"))) {
            for (int i = 0; i < number; i++) {
                gson.toJson(generateSegment(), bw);
                bw.newLine();
                System.out.println(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Segment generateSegment() {
        ImageCaptcha captcha = new ImageCaptcha.Builder(40, 50)
                .addContent(new MyContentProducer(1), new MyWordRenderer())
                .addBackground(new FlatColorBackgroundProducer())
                .addFilter(new RippleImageFilter()).build();
        Image image = new Image(captcha.getImage()).clear().skeletonization();
        Segment segment = image.segmentation().get(0);
        segment.setSymbol(captcha.getContent());
        return segment;
    }
}
