package game;

import java.awt.*;
import java.awt.event.*;

// Element representing the player-controlled shape
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



    public PlayerShape() {
        super(playerShapePoints, new Point(400, 500), 90); 
        // Starting position (center bottom of the screen)
    }
    
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


    public void setForwardPressed(boolean value) {
        forwardPressed = value;
    }

    public void setLeftPressed(boolean value) {
        leftPressed = value;
    }

    public void setRightPressed(boolean value) {
        rightPressed = value;
    }

    public void reduceHealth(int points) {
        health -= points;
    }

    public int getHealth() {
        return health;
    }

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
    
    // Inner class for handling key events
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
