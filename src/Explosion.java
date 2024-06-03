import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion {
    private double xCoord;
    private double yCoord;
    private BufferedImage image;

    public Explosion(Enemy en) {
        xCoord = en.getxCoord();
        yCoord = en.getyCoord();
        try {
            image = ImageIO.read(new File("src/ExplosionPowerup.png"));
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

    public BufferedImage getImage() {
        return image;
    }

    public void move() {
        yCoord -= 0.1;
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle explosionIconRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}