package Lab02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FlappyBirdGame extends JFrame implements ActionListener, KeyListener {

    // Window configuration
    private final int width = 360;
    private final int height = 640;

    // Bird object
    private int birdX = width / 8;
    private int birdY = height / 2;
    private int birdWidth = 34;
    private int birdHeight = 24;
    private Image birdImg;
    private int velocityY = 0;
    private final int gravity = 1;

    // Pipe object
    private int pipeWidth = 64;
    private int pipeHeight = 512;
    private Image topPipeImg;
    private Image bottomPipeImg;
    private ArrayList<Pipe> pipes;
    private Timer pipeTimer;

    // Game management
    private boolean gameOver = false;
    private double score = 0;
    private Image bgImg;
    private Timer gameLoop;

    // Pipe class
    class Pipe {
        int x = width;
        int y, w, h;
        Image img;
        boolean passed = false;

        Pipe(Image img, int y, int h) {
            this.y = y;
            this.w = pipeWidth;
            this.h = h;
            this.img = img;
        }
    }

    public FlappyBirdGame() {
        setTitle("Flappy Bird");
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load assets
        bgImg = new ImageIcon("./assets/flappybirdbg.png").getImage();
        birdImg = new ImageIcon("./assets/flappybird.png").getImage();
        topPipeImg = new ImageIcon("./assets/toppipe.png").getImage();
        bottomPipeImg = new ImageIcon("./assets/bottompipe.png").getImage();

        pipes = new ArrayList<>();

        // Game Loop
        gameLoop = new Timer(20, this);
        gameLoop.start();

        // Timer for pipe generation
        pipeTimer = new Timer(1500, e -> placePipes());
        pipeTimer.start();

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    public void placePipes() {
        int randomPipeY = (int) (0 - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = height / 4;

        pipes.add(new Pipe(topPipeImg, randomPipeY, pipeHeight));
        pipes.add(new Pipe(bottomPipeImg, randomPipeY + pipeHeight + openingSpace, pipeHeight));
    }

    public void move() {
        if (gameOver)
            return;

        // Bird physics
        velocityY += gravity;
        birdY += velocityY;
        birdY = Math.max(0, birdY); // Make sure the bird is no too high

        // Lose if the bird is below the screen
        if (birdY > height)
            gameOver = true;

        // Pipe move and check collision
        for (Pipe p : pipes) {
            p.x -= 4;

            // Add score if the bird passes the pipe
            if (!p.passed && birdX > p.x + p.w) {
                p.passed = true;
                score += 0.5; // Because 1 pair of pipes has 2 pipes, adding 0.5 for each pipe is 1 point
            }

            // Collision with pipe
            if (checkCollision(p)) {
                gameOver = true;
            }
        }
    }

    // Check collision using Rectangle
    public boolean checkCollision(Pipe p) {
        return birdX < p.x + p.w &&
                birdX + birdWidth > p.x &&
                birdY < p.y + p.h &&
                birdY + birdHeight > p.y;
    }

    // Restart game
    public void restartGame() {
        birdY = height / 2;
        velocityY = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
        gameLoop.start();
        pipeTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        Image offscreen = createImage(width, height);
        Graphics offgc = offscreen.getGraphics();
        draw(offgc);
        g.drawImage(offscreen, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(bgImg, 0, 0, width, height, null);
        g.drawImage(birdImg, birdX, birdY, birdWidth, birdHeight, null);

        for (Pipe p : pipes) {
            g.drawImage(p.img, p.x, p.y, p.w, p.h, null);
        }

        // Print score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        if (gameOver) {
            g.drawString("Game Over: " + (int) score, 60, height / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press ENTER to Restart", 70, height / 2 + 40);
        } else {
            g.drawString(String.valueOf((int) score), 10, 70);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
            pipeTimer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -10;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
            restartGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        new FlappyBirdGame();
    }
}