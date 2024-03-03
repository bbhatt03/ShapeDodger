package game;

import java.awt.*;
import java.awt.event.*;

/**
 * The PlayerShape class represents the player-controlled shape in the game.
 * It extends the Polygon class to define the shape of the player's character.
 * This class handles movement, collision detection, and health management of the player.
 */
class PlayerShape extends Polygon {
    private boolean forwardPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private final double stepSize = 5.0;
    private int health = 100; // Initial health value

    private static final int BASE_WIDTH = 20; // Width of the base of the triangle
    private static final int HEIGHT = 30; // Height of the triangle
    private static final Point[] playerShapePoints = {
            new Point(0, 0),    // Top tip
            new Point(-BASE_WIDTH / 2, HEIGHT), // Bottom-left corner
            new Point(BASE_WIDTH / 2, HEIGHT)  // Bottom-right corner
    };

    /**
     * Constructs a PlayerShape object with default parameters.
     * The initial position is set to the center bottom of the screen,
     * and the initial rotation is set to 90 degrees (facing upwards).
     */
    public PlayerShape() {
        super(playerShapePoints, new Point(400, 500), 90); 
    }
    
    /**
     * Moves the player shape based on the current input.
     * If forward is pressed, moves the player forward in the direction they are facing.
     * If left or right is pressed, rotates the player left or right.
     */
    public void move() {
        if (forwardPressed) {
            double deltaX = stepSize * Math.sin(Math.toRadians(rotation));
            double deltaY = stepSize * Math.cos(Math.toRadians(rotation));
            position.x += deltaX;
            position.y -= deltaY; // Subtract deltaY to move upwards towards the top tip
        }
        if (leftPressed) {
            rotation -= 5; // Rotate left by 5 degrees
        }
        if (rightPressed) {
            rotation += 5; // Rotate right by 5 degrees
        }
    }

    /**
     * Sets the forward movement state of the player.
     * @param value true if forward movement is enabled, false otherwise
     */
    public void setForwardPressed(boolean value) {
        forwardPressed = value;
    }

    /**
     * Sets the left movement state of the player.
     * @param value true if left movement is enabled, false otherwise
     */
    public void setLeftPressed(boolean value) {
        leftPressed = value;
    }

    /**
     * Sets the right movement state of the player.
     * @param value true if right movement is enabled, false otherwise
     */
    public void setRightPressed(boolean value) {
        rightPressed = value;
    }

    /**
     * Reduces the health of the player by the specified amount.
     * @param points the amount of health points to reduce
     */
    public void reduceHealth(int points) {
        health -= points;
    }

    /**
     * Gets the current health of the player.
     * @return the current health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Paints the player shape on the graphics context.
     * The player shape is filled with blue color, and the health is displayed as text.
     * @param brush the graphics context to paint on
     */
    public void paint(Graphics brush) {
        brush.setColor(Color.BLUE);
        Point[] points = getPoints();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].x;
            yPoints[i] = (int) points[i].y;
        }
        brush.fillPolygon(xPoints, yPoints, points.length);
        brush.setColor(Color.WHITE);
        brush.drawString("Health: " + health, 10, 20); // Display health counter
    }

    /**
     * Checks whether the player shape collides with the specified polygon.
     * @param other the polygon to check for collision with
     * @return true if the player shape collides with the specified polygon, false otherwise
     */
    public boolean collides(Polygon other) {
        // We'll implement collision detection logic here
        Point[] points = getPoints();
        for (Point point : points) {
            if (other.contains(point)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Inner class for handling key events related to player movement.
     */
    class KeyHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP) {
                setForwardPressed(true);
            }
            if (key == KeyEvent.VK_LEFT) {
                setLeftPressed(true);
            }
            if (key == KeyEvent.VK_RIGHT) {
                setRightPressed(true);
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP) {
                setForwardPressed(false);
            }
            if (key == KeyEvent.VK_LEFT) {
                setLeftPressed(false);
            }
            if (key == KeyEvent.VK_RIGHT) {
                setRightPressed(false);
            }
        }
    }
}
