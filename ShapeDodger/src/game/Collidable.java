package game;

/**
 * The Collidable interface represents an object that can collide with other polygons.
 * Classes implementing this interface must provide an implementation for the collides method,
 * which checks whether the object collides with another polygon.
 */
interface Collidable {
    
    /**
     * Checks whether the object collides with the specified polygon.
     *
     * @param other the polygon to check for collision with
     * @return true if the object collides with the specified polygon, false otherwise
     */
    boolean collides(Polygon other);
}
