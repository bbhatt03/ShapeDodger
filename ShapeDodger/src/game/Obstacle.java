package game;
import java.awt.*;
import java.awt.event.*;

class Obstacle extends Polygon {
	private final boolean isBig;

    public Obstacle(Point[] inShape, Point inPosition, double inRotation, boolean isBig) {
        super(inShape, inPosition, inRotation);
        this.isBig = isBig;
    }

    public boolean isBig() {
        return isBig;
    }

    public void paint(Graphics brush) {
        if (isBig)
            brush.setColor(Color.red);
        else
            brush.setColor(Color.green);
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

