package game;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

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

        // Add key listener using KeyHandler from PlayerShape class
        this.addKeyListener(player.new KeyHandler());

        // Set focusable and request focus
        this.setFocusable(true);
        this.requestFocus();
    }

    public void paint(Graphics brush) {
        brush.setColor(Color.BLACK);
        brush.fillRect(0, 0, width, height);
        
        brush.setColor(Color.RED);
        brush.drawString("Deadly Obstacle: INSTANT GAME OVER", 700, 20);
        
        brush.setColor(Color.GREEN);
        brush.drawString("Poison Obstacle: -5 HEALTH PER SECOND", 700, 35);

        if (!gameOver) {
            player.move();

            //Lambda Expression
            Arrays.stream(obstacles).forEach(obstacle -> {
                obstacle.paint(brush);
                if (player.collides(obstacle)) {
                    if (obstacle.isBig()) {
                        gameOver = true; // Game over if player hits a big obstacle
                    } else {
                        player.reduceHealth(1); // Reduce player's health if hits a small obstacle
                        if (player.getHealth() <= 0) {
                            gameOver = true; // Game over if player's health reaches 0
                        }
                    }
                }
            });
            player.paint(brush);
        } else {
            brush.setColor(Color.WHITE);
            brush.drawString("Game Over!", 350, 300);
        }
    }

    public static void main(String[] args) {
        ShapeDodger game = new ShapeDodger();
        game.repaint();
    }
}
