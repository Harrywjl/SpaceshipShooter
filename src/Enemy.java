import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    static private double speed = 0.1;
    private double xCoord;
    private double yCoord;
    private BufferedImage image;

    public Enemy() {
        xCoord = 600;
        yCoord = (int) (Math.random() * 350);
        try {
            image = ImageIO.read(new File("src/Enemy.png"));
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
        if (xCoord - speed > -10) {
            xCoord -= speed;
        }
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle enemyRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}