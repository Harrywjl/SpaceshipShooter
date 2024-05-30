import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private double speed;
    private double xCoord;
    private double yCoord;
    private boolean random;
    private boolean up;
    private BufferedImage image;

    public Enemy() {
        speed = (int) (Math.random() * 101) / 500.0;
        if (speed < 0.05) {
            speed += 0.05;
        }
        xCoord = 600;
        yCoord = (int) (Math.random() * 350);
        random = (int) (Math.random() * 2) == 1;
        up = (int) (Math.random() * 2) == 1;
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
        if (random) {
            if (up) {
                if (yCoord - speed >= 0) {
                    yCoord -= speed;
                }
                if (yCoord < 10) {
                    up = false;
                }
            } else {
                if (yCoord + speed <= 349 && yCoord < 340) {
                    yCoord += speed;
                }
                if (yCoord > 340) {
                    up = true;
                }
            }
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