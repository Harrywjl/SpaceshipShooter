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
    private ArrayList<Explosion> explosions;
    private long lastPressProcessed = 0;
    private int enemyInterval = 0;
    private ExplosionAnimation animation = null;
    public GraphicsPanel() {
        try {
            background = ImageIO.read(new File("src/Background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player = new Player("src/Spaceship.png");
        enemies = new ArrayList<Enemy>();
        projectiles = new ArrayList<Projectile>();
        explosions = new ArrayList<Explosion>();
        pressedKeys = new boolean[128];
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        if (player.getStatus()) {
            g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
            for (int p = 0; p < projectiles.size(); p++) {
                Projectile proj = projectiles.get(p);
                g.drawImage(proj.getImage(), proj.getxCoord(), proj.getyCoord(), null);
                proj.move();
                if (proj.getxCoord() > 600) {
                    projectiles.remove(p);
                    p--;
                }
            }
            for (int i = 0; i < explosions.size(); i++) {
                Explosion e = explosions.get(i);
                g.drawImage(e.getImage(), e.getxCoord(), e.getyCoord(), null);
                e.move();
                if (e.explosionIconRect().intersects(player.playerRect())) {
                    animation = new ExplosionAnimation();
                    explosions.remove(i);
                    i--;
                }
            }
            if (animation != null) {
                if (animation.getTimer() < 14000) {
                    g.drawImage(animation.getImage(), animation.getxCoord(), animation.getyCoord(), null);
                } else {
                    animation = null;
                }
            }
            Thread thread = new Thread(runnable);
            thread.start();
            enemyInterval++;
            for (int e = 0; e < enemies.size(); e++) {
                if (enemies.get(e).getxCoord() <= 0) {
                    enemies.remove(e);
                    e--;
                }
                if (animation != null) {
                    if (animation.explosionRect().intersects(enemies.get(e).enemyRect())) {
                        enemies.remove(e);
                        e--;
                        player.killEnemy();
                    }
                }
            }
            for (int e = 0; e < enemies.size(); e++) {
                Enemy enemy = enemies.get(e);
                g.drawImage(enemy.getImage(), enemy.getxCoord(), enemy.getyCoord(), null);
                enemy.move();
                if (player.playerRect().intersects(enemy.enemyRect())) { // check for collision
                    player.gameOver();
                }
                for (int p = 0; p < projectiles.size(); p++) {
                    Projectile proj = projectiles.get(p);
                    if (proj.projectileRect().intersects(enemy.enemyRect())) { // check for collision
                        player.killEnemy();
                        if ((int) (Math.random() * 15) == 0) { // chances of explosion power up drop
                            explosions.add(new Explosion(enemy));
                        }
                        enemies.remove(e);
                        e--;
                        projectiles.remove(p);
                        p--;
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
        } else {
            g.setFont(new Font("Courier New", Font.BOLD, 80));
            g.drawString("GAME OVER", 75, 125);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("FINAL Score: " + player.getScore(), 50, 200);
        }

    }

    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        // player shoots (space)
        if (key == 32 && System.currentTimeMillis() - lastPressProcessed > 50) {
            projectiles.add(new Projectile(player));
            lastPressProcessed = System.currentTimeMillis();
        }
        pressedKeys[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    // ----- Periodically create enemies -----
    Runnable runnable = new Runnable() {
        public void run() {
            while (enemyInterval > 200) {
                enemies.add(new Enemy());
                enemyInterval -= 200;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}