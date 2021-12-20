package war.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class Resource
{
    public static Random rand = new Random();
    public static final int minQuantity = 5;
    public static int resourceQuantity = 0;

    private int quantity;
    private int size;
    private Position pos;
    private Circle c;
    private Pane world;

    public Resource(Pane world, ArrayList<Ant> ants)
    {
        this.quantity = rand.nextInt(5) + minQuantity;
        this.world = world;
        this.size = 10 * quantity;
        this.c = new Circle(size, Color.YELLOW);
        c.setStroke(Color.BLACK);
        this.pos = new Position(world, size);
        boolean valid = false;
        while (!valid)
        {
            for (Ant ant : ants)
            {
                if (pos.collision(ant.getPos(), size))
                {
                    this.pos = new Position(world, size);
                    break;
                }
                valid = true;
            }

        }
        world.getChildren().add(c);
        resourceQuantity++;
    }

    public void draw()
    {
        c.setRadius(size);
        c.setTranslateX(pos.getX());
        c.setTranslateY(pos.getY());
    }

    public void remove()
    {
        world.getChildren().remove(c);
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getSize()
    {
        return size;
    }

    public void updateSize()
    {
        size = 10 * quantity;
    }

    public Position getPos()
    {
        return pos;
    }
}
