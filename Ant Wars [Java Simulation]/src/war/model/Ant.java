package war.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Random;

public abstract class Ant
{
    public static final int defaultRadius = 5;
    public static Random rand = new Random();
    public static HashMap<Color, Boolean> ids = new HashMap<Color, Boolean>();

    private State state;
    private int radius;
    private Position pos;
    private Heading heading;
    private Circle c;
    private Pane world;
    private Color id;
    private int maxHealth = 5;
    private int health = maxHealth;
    private int perceptualRange;
    private int attackRange;
    private int resources;

    public Ant(State state, Pane world)
    {
        this(state, world, null);
    }

    public Ant(State state, Pane world, Queen queen)
    {
        // If no queen is given, generate a queen
        if (queen == null)
        {
            // Generate an id for each queen/colony
            generateID();
            // Ensure the generated id is unique
            while (ids.containsKey(id))
            {
                generateID();
            }
            ids.put(id, true);
            this.pos = new Position(world, radius);
            this.radius = defaultRadius * 2;
        }
        else
        {
            id = queen.getId();
            this.pos = new Position(queen.getPos().getX(), queen.getPos().getY());
            this.radius = defaultRadius;
        }

        this.state = state;
        this.heading = new Heading();
        this.c = new Circle(radius, id);
        this.world = world;
        c.setStroke(Color.BLACK);
        world.getChildren().add(c);
    }

    public abstract void move();

    public void draw()
    {
        c.setRadius(radius);
        c.setTranslateX(pos.getX());
        c.setTranslateY(pos.getY());
    }

    public void remove()
    {
        world.getChildren().remove(c);
    }

    public void generateID()
    {
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();

        id = Color.color(red, green, blue);
    }

    public abstract void collisionCheck(Object other);

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
        c.setStroke(state.getColor());
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public Color getId()
    {
        return id;
    }

    public Position getPos()
    {
        return pos;
    }

    public int getRadius()
    {
        return radius;
    }

    public int getPerceptualRange()
    {
        return perceptualRange;
    }

    public void setPerceptualRange(int perceptualRange)
    {
        this.perceptualRange = perceptualRange;
    }

    public int getAttackRange()
    {
        return attackRange;
    }

    public void setAttackRange(int attackRange)
    {
        this.attackRange = attackRange;
    }

    public int getResources()
    {
        return resources;
    }

    public void setResources(int resources)
    {
        this.resources = resources;
    }

    public Heading getHeading()
    {
        return heading;
    }

    public void setHeading(Heading heading)
    {
        this.heading = heading;
    }

    public Pane getWorld()
    {
        return world;
    }
}
