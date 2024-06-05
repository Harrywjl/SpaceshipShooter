import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExplosionAnimation {
    private double xCoord;
    private double yCoord;
    private ArrayList<BufferedImage> images;
    private int time = 0;

    public ExplosionAnimation() {
        xCoord = 275;
        yCoord = 90;
        images = new ArrayList<BufferedImage>();
        try {
            images.add(ImageIO.read(new File("src/Explosion1.png")));
            images.add(ImageIO.read(new File("src/Explosion2.png")));
            images.add(ImageIO.read(new File("src/Explosion3.png")));
            images.add(ImageIO.read(new File("src/Explosion4.png")));
            images.add(ImageIO.read(new File("src/Explosion5.png")));
            images.add(ImageIO.read(new File("src/Explosion6.png")));
            images.add(ImageIO.read(new File("src/Explosion7.png")));
            images.add(ImageIO.read(new File("src/Explosion8.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }
    public int getTimer() {
       return time;
    }

    public BufferedImage getImage() {
        time++;
        int img = time / 1750;
        if (img < 8) {
            return images.get(img);
        }
        return images.get(7);
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle explosionRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}