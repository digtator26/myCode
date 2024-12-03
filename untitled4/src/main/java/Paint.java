import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Paint extends JPanel {

    private final BufferedImage bufferedImage;

    private Paint(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0,0, 1000, 1000);
        g.drawImage(bufferedImage, 30,25,this);
    }

    public static void doIt(BufferedImage bufferedImage) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new Paint(bufferedImage));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(bufferedImage.getWidth() + 100, bufferedImage.getHeight() + 100);
        frame.setVisible(true);

        /*BufferedImage img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        frame.print(img.getGraphics());
        try {
            ImageIO.write(img, "jpg", new File("panel1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Container container = frame.getContentPane();
        BufferedImage img = new BufferedImage(container.getWidth(), container.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        container.printAll(g2d);
        g2d.dispose();
        try {
            ImageIO.write(img, "jpg", new File("panel.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setVisible(false);
        frame.dispose();
    }

}
