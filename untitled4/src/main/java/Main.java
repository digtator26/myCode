import net.logicsquad.nanocaptcha.image.ImageCaptcha;
import net.logicsquad.nanocaptcha.image.backgrounds.FlatColorBackgroundProducer;
import net.logicsquad.nanocaptcha.image.filter.RippleImageFilter;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        //BaseSegments.generation();
        // 1 этап
        ImageCaptcha captcha = new ImageCaptcha.Builder(220, 50)
                .addContent(new MyContentProducer(), new MyWordRenderer())
                .addBackground(new FlatColorBackgroundProducer())
                .addFilter(new RippleImageFilter()).build();
        Image image = new Image(captcha.getImage()).draw();
        System.out.println();
                // 2 этап
                /*.clear().draw()
                .skeletonization().draw();
        // 3 этап
        List<Segment> segments = image.segmentation();
        // 4 этап
        BaseSegments baseSegments = new BaseSegments();
        // 5 этап
        System.out.println("Сгенерированная: " + captcha.getContent());
        for (Segment segment: segments) {
            double probability = -1;
            Segment suitable = segment;
            for (int i = 0; i < baseSegments.size(); i++) {
                double prob = segment.similarity(baseSegments.get(i));
                if (probability < prob) {
                    probability = prob;
                    suitable = baseSegments.get(i);
                }
            }
            System.out.println(suitable.getSymbol() + " - " + probability);
        }*/
    }

}
