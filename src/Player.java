import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    private final double MOVE_AMT = 0.15;
    private BufferedImage image;
    private double xCoord;
    private double yCoord;
    private int score;
    private boolean alive;

    public Player(String img) {
        xCoord = 50;
        yCoord = 175;
        score = 0;
        alive = true;
        try {
            image = ImageIO.read(new File(img));
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

    public int getScore() {
        return score;
    }

    public boolean getStatus() {
        return alive;
    }

    public void moveRight() {
        if (xCoord + MOVE_AMT <= 550) {
            xCoord += MOVE_AMT;
        }
    }

    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public void moveUp() {
        if (yCoord - MOVE_AMT >= 0) {
            yCoord -= MOVE_AMT;
        }
    }

    public void moveDown() {
        if (yCoord + MOVE_AMT <= 349) {
            yCoord += MOVE_AMT;
        }
    }

    public void killEnemy() {
        score += 100;
    }

    public void gameOver() {
        alive = false;
    }

    public BufferedImage getPlayerImage() {
        return image;
    }

    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight() - 15;
        int imageWidth = getPlayerImage().getWidth() - 15;
        Rectangle rect = new Rectangle((int) xCoord + 8, (int) yCoord + 8, imageWidth, imageHeight);
        return rect;
    }
}