import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Projectile {
    static private double speed = 0.25;
    private double xCoord;
    private double yCoord;
    private BufferedImage image;

    public Projectile(Player s) {
        if (s.getStatus()) {
            xCoord = s.getxCoord() + 45;
            yCoord = s.getyCoord() + 21;
            try {
                image = ImageIO.read(new File("src/Projectile.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
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
        if (xCoord + speed < 610) {
            xCoord += speed;
        }
    }

    public Rectangle projectileRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}