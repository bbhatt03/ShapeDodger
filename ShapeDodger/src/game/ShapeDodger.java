package game;

/*
CLASS: ShapeDodger
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;

class ShapeDodger extends Game {
	private PlayerShape player;
    private Obstacle[] obstacles;
    private boolean gameOver = false;
    private ScoreTracker scoreTracker;
    private static final int BIG_OBSTACLE_SIZE = 50;

    public ShapeDodger() {
        super("Shape Dodger", 1000, 800);
        player = new PlayerShape();
        obstacles = new Obstacle[30]; // Create 20 obstacles
        scoreTracker = new ScoreTracker();

        // Initialize obstacles
        for (int i = 0; i < obstacles.length; i++) {
            int obstacleSize = (int) (Math.random() * 2); // 0 for small, 1 for big
            boolean isBig = obstacleSize == 1;
            Point[] obstaclePoints;
            if (isBig) {
                // Make big obstacle 5 times larger
                obstaclePoints = new Point[]{
                        new Point(-BIG_OBSTACLE_SIZE / 2, -BIG_OBSTACLE_SIZE / 2),
                        new Point(BIG_OBSTACLE_SIZE / 2, -BIG_OBSTACLE_SIZE / 2),
                        new Point(BIG_OBSTACLE_SIZE / 2, BIG_OBSTACLE_SIZE / 2),
                        new Point(-BIG_OBSTACLE_SIZE / 2, BIG_OBSTACLE_SIZE / 2)
                };
            } else {
                obstaclePoints = new Point[]{
                        new Point(0, 0),
                        new Point(20, 0),
                        new Point(20, 20),
                        new Point(0, 20)
                };
            }
            obstacles[i] = new Obstacle(
                    obstaclePoints,
                    new Point((int) (Math.random() * 800), (int) (Math.random() * 600)),
                    0, isBig
            );
        }

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                player.keyTyped(e);
            }
        });
        this.setFocusable(true);
        this.requestFocus();
    }

    public void paint(Graphics brush) {
        brush.setColor(Color.black);
        brush.fillRect(0, 0, width, height);
        
        brush.setColor(Color.red);
        brush.drawString("Deadly Obstacle: INSTANT GAME OVER", 700, 20);
        
        brush.setColor(Color.green);
        brush.drawString("Poison Obstacle: -5 HEALTH", 700, 35);

        if (!gameOver) {
            player.move();
            for (Obstacle obstacle : obstacles) {
                obstacle.paint(brush);
                if (player.collides(obstacle)) {
                    if (obstacle.isBig()) {
                        gameOver = true; // Game over if player hits a big obstacle
                    } else {
                        player.reduceHealth(5); // Reduce player's health if hits a small obstacle
                        if (player.getHealth() <= 0) {
                            gameOver = true; // Game over if player's health reaches 0
                        }
                    }
                }
            }
            player.paint(brush);
        } else {
            brush.setColor(Color.white);
            brush.drawString("Game Over!", 350, 300);
        }
    }

    public static void main(String[] args) {
        ShapeDodger game = new ShapeDodger();
        game.repaint();
    }
}