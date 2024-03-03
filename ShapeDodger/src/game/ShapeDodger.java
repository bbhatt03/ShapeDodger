package game;

import java.awt.*;
import java.util.Arrays;

/**
 * The ShapeDodger class represents the main game control and rendering logic for the Shape Dodger game.
 * It extends the Game class and handles player movement, obstacle creation, collision detection,
 * and game-over conditions.
 */
class ShapeDodger extends Game {
    private PlayerShape player;
    private Obstacle[] obstacles;
    private boolean gameOver = false;
    private ScoreTracker scoreTracker;
    private static final int BIG_OBSTACLE_SIZE = 50;

    /**
     * Constructs a new instance of ShapeDodger with default dimensions and initializes the game elements.
     */
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

    /**
     * Paints the game elements on the screen.
     *
     * @param brush the graphics context on which to paint
     */
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
            Arrays.stream(obstacles).forEach(obstacle -> {// Creates an instance
                //of the anonymous inner class
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

    /**
     * The main method to start the game.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        ShapeDodger game = new ShapeDodger();
        game.repaint();
    }

    /**
     * The Obstacle class represents the obstacles in the game.
     * It extends the Polygon class and is used to draw and manage obstacles.
     */
    class Obstacle extends Polygon {
        private final boolean isBig;

        /**
         * Constructs a new Obstacle instance with the specified parameters.
         *
         * @param inShape    the shape of the obstacle
         * @param inPosition the position of the obstacle
         * @param inRotation the rotation angle of the obstacle
         * @param isBig      true if the obstacle is big, false otherwise
         */
        public Obstacle(Point[] inShape, Point inPosition, double inRotation, boolean isBig) {
            super(inShape, inPosition, inRotation);
            this.isBig = isBig;
        }

        /**
         * Checks if the obstacle is big.
         *
         * @return true if the obstacle is big, false otherwise
         */
        public boolean isBig() {
            return isBig;
        }

        /**
         * Paints the obstacle on the graphics context.
         *
         * @param brush the graphics context on which to paint
         */
        public void paint(Graphics brush) {
            if (isBig) {
                brush.setColor(Color.red);
            } else {
                brush.setColor(Color.green);
            }
            Point[] points = getPoints();
            int[] xPoints = new int[points.length];
            int[] yPoints = new int[points.length];
            for (int i = 0; i < points.length; i++) {
                xPoints[i] = (int) points[i].x;
                yPoints[i] = (int) points[i].y;
            }
            brush.fillPolygon(xPoints, yPoints, points.length);
        }
    }
}
