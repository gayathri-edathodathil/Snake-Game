import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        int x, y;

        Tile(int x, int y) {
            this.x = x * tilesize;
            this.y = y * tilesize;
        }
    }

    int width, height;
    private int tilesize = 25;

    // snake
    Tile snakehead;
    ArrayList<Tile> snakebody;

    // food
    Tile food;

    // placefood
    Random foodpos;

    // move snake
    Timer movesnake;
    int velocityX = 0;
    int velocityY = 0;
    boolean gameover = false;

    SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        addKeyListener(this);
        setFocusable(true);

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.darkGray);

        snakehead = new Tile(5, 5);
        snakebody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        foodpos = new Random();
        makefood();

        movesnake = new Timer(180, this);
        movesnake.start();
    }

    public void makefood() {
        food.x = (foodpos.nextInt(width/tilesize)) * tilesize;
        food.y = (foodpos.nextInt(height/tilesize)) * tilesize;
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        // snakehead
        g.setColor(Color.green);
        g.fill3DRect(snakehead.x, snakehead.y, tilesize, tilesize, true);
        // food
        g.setColor(Color.RED);
        g.fill3DRect(food.x, food.y, tilesize, tilesize, true);
        // snakebody
        for (Tile part : snakebody) {
            g.setColor(Color.green);
            g.fill3DRect(part.x, part.y, tilesize, tilesize, true);
        }

        // score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameover) {
            g.setColor(Color.red);
            g.drawString("Game Over! Score: " + String.valueOf(snakebody.size()), tilesize, tilesize);
        } else {
            g.setColor(Color.white);
            g.drawString("Score: " + String.valueOf(snakebody.size()), tilesize, tilesize);
        }
    }

    public void move() {
        // eat food
        if (collision(snakehead, food)) {
            snakebody.add(new Tile((food.x) / tilesize, (food.y) / tilesize));
            makefood();
        }

        Tile prev = snakehead;
        for (int i = snakebody.size() - 1; i >= 0; i--) {
            Tile part = snakebody.get(i);
            if (i == 0) {
                part.x = prev.x;
                part.y = prev.y;
            } else {
                Tile prevpart = snakebody.get(i - 1);
                part.x = prevpart.x;
                part.y = prevpart.y;
            }
        }
        snakehead.x += velocityX;
        snakehead.y += velocityY;

        for (Tile part : snakebody) {
            if (collision(part, snakehead)) {
                gameover = true;
            }
        }
        if (snakehead.x < 0 || snakehead.x >width || snakehead.y < 0
                || snakehead.y > height) {
            gameover = true;
        }
    }
    
    public void resetGame() {
        snakehead = new Tile(5, 5); 
        snakebody.clear();        
        makefood();                
        velocityX = 0;
        velocityY = 25;
        gameover = false;
        snakebody.clear();        
        movesnake.start();         
        repaint();                 
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameover) {
            movesnake.stop();
            int option = JOptionPane.showConfirmDialog(null, "You Lost! Would you like to restart the game?",
                    "Game Over", JOptionPane.YES_NO_OPTION);
            if (option == 1) {
                System.exit(0);
            } else {
                resetGame();
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int dir = e.getKeyCode();
        if (dir == KeyEvent.VK_LEFT && velocityX != 25) {
            velocityX = -25;
            velocityY = 0;
        } else if (dir == KeyEvent.VK_UP && velocityY != 25) {
            velocityX = -0;
            velocityY = -25;
        } else if (dir == KeyEvent.VK_RIGHT && velocityX != -25) {
            velocityX = 25;
            velocityY = 0;
        } else if (dir == KeyEvent.VK_DOWN && velocityY != -25) {
            velocityX = 0;
            velocityY = 25;
        }
    }

    // dont need
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}