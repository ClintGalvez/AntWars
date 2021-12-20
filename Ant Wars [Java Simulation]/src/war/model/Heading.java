package war.model;

public class Heading
{
    public static final double SPEED = 5;
    private double dx; // Change of x
    private double dy; // Change of y

    /**
     * Constructs a specified heading
     * @param dx
     * @param dy
     */
    public Heading(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public Heading(Position currentPos, Position destination)
    {
        double dir = Math.atan2(destination.getY() - currentPos.getY(), destination.getX() - currentPos.getX());
        dx = Math.cos(dir);
        dy = Math.sin(dir);
    }

    /**
     * Constructs a random heading
     */
    public Heading()
    {
        double dir = Math.random() * 2 * Math.PI; // Generate a random angle
        dx = Math.cos(dir); // Get and set the x of that angle
        dy = Math.sin(dir); // Get and set the y of that angle
    }

    public double getDx()
    {
        return dx * SPEED;
    }

    public double getDy()
    {
        return dy * SPEED;
    }

    public void bounceX()
    {
        dx *= -1;
    }

    public void bounceY()
    {
        dy *= -1;
    }
}