import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Projectile {
    private int xCoord;
    private int yCoord;
    private BufferedImage image;

    public Projectile(Player s) {
        xCoord = s.getxCoord() + 25;
        yCoord = s.getyCoord();
        try {
            image = ImageIO.read(new File("src/Projectile.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void move() {
        if (xCoord + 1 <= 600) {
            xCoord += 1;
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public Rectangle projectileRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle(xCoord, yCoord, imageWidth, imageHeight);
        return rect;
    }
}