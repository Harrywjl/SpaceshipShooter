import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener {
    private BufferedImage background;
    private Player player;
    private boolean[] pressedKeys;
    private ArrayList<Enemy> enemies;
    private ArrayList<Projectile> projectiles;

    public GraphicsPanel() {
        try {
            background = ImageIO.read(new File("src/Background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player = new Player("src/Spaceship.png");
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        pressedKeys = new boolean[128]; // 128 keys on keyboard, max keycode is 127
        addKeyListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);  // the order that things get "painted" matter; we put background down first
        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);

        for (int e = 0; e < enemies.size(); e++) {
            Enemy enemy = enemies.get(e);
            g.drawImage(enemy.getImage(), enemy.getxCoord(), enemy.getyCoord(), null); // draw Coin
            for (int p = 0; p < projectiles.size(); p++) {
                Projectile proj = projectiles.get(p);
                proj.move();
                g.drawImage(proj.getImage(), proj.getxCoord(), proj.getyCoord(), null);
                if (proj.projectileRect().intersects(enemy.enemyRect())) { // check for collision
                    player.killEnemy();
                    enemies.remove(e);
                    e--;
                }
            }
        }

        // draw score
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString("Score: " + player.getScore(), 20, 40);

        // player moves left (A)
        if (pressedKeys[65]) {
            player.moveLeft();
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            player.moveRight();
        }

        // player moves up (W)
        if (pressedKeys[87]) {
            player.moveUp();
        }

        // player moves down (S)
        if (pressedKeys[83]) {
            player.moveDown();
        }

        // player shoots (space)
        if (pressedKeys[32]) {
            Projectile p = new Projectile(player);
            projectiles.add(p);
        }
    }

    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented

    public void keyPressed(KeyEvent e) {
        // see this for all keycodes: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        // A = 65, D = 68, S = 83, W = 87, left = 37, up = 38, right = 39, down = 40, space = 32, enter = 10
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

}