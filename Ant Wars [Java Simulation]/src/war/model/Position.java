package war.model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Position
{
    private double x;
    private double y;

    /**
     * Constructs a position with given x and y coordinates
     * @param x x-coordinate of the position
     * @param y y-coordinate of the posittion
     */
    public Position(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a position on a random point in a given world/pane for a circle
     * @param world
     * @param radius
     */
    public Position(Pane world, int radius)
    {
        this(radius + Math.random() * (world.getWidth() - 2 * radius),
                radius + Math.random() * (world.getHeight() - 2 * radius));
    }

    /**
     * Creates a position on a random point in a given world/pane for a rectangle
     * @param world
     * @param r
     */
    public Position(Pane world, Rectangle r)
    {
        this(Math.random() * (world.getWidth() - r.getWidth()),
                Math.random() * (world.getHeight() - r.getHeight()));
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    /**
     * Calculates the distance between current position and other given position
     * @param other
     * @return distance between current position and the other given position
     */
    public double distance(Position other)
    {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Moves the position towards a given heading in a given world/pane
     * @param heading
     * @param world
     * @param radius
     */
    public void move(Heading heading, Pane world, int radius)
    {
        x += heading.getDx();
        y += heading.getDy();

        // Prevention from moving out of bounds of the pane
        if (x < radius || x > world.getWidth() - radius)
        {
            heading.bounceX();
            x += heading.getDx();
        }
        if (y < radius || y > world.getHeight() - radius)
        {
            heading.bounceY();
            y += heading.getDy();
        }
    }

    public boolean collision(Position other, int radius)
    {
        return distance(other) < radius; //return distance(other) < 2 * radius;
    }

    @Override
    public String toString()
    {
        return x + "" + y;
    }
}
