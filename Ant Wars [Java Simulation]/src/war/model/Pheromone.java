package war.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pheromone
{
    private int radius;
    private State state;
    private Position pos;
    private Circle c;
    private float relevance;
    private Pane world;
    private Color color;
    private Color id;

    public Pheromone(Pane world, State state, Position pos, Color id, int radius)
    {
        this.world = world;
        this.id = id;
        this.radius = radius;
        this.state = state;
        this.pos = new Position(pos.getX(), pos.getY());
        this.relevance = 0.9F;
        this.color = new Color(state.getColor().getRed(), state.getColor().getGreen(), state.getColor().getBlue(), relevance);
        this.c = new Circle(radius, color);
        this.c.setStroke(Color.BLACK);
        world.getChildren().add(c);
    }

    public int getRadius()
    {
        return radius;
    }

    public Position getPos()
    {
        return pos;
    }

    public void remove()
    {
        world.getChildren().remove(c);
    }

    public float getRelevance()
    {
        return relevance;
    }

    public void updateRelevance()
    {
        relevance *= 0.995;
        updateColor();
    }

    public void restoreRelevance()
    {
        relevance = 0.9F;
    }

    public void updateColor()
    {
        color = new Color(state.getColor().getRed(), state.getColor().getGreen(), state.getColor().getBlue(), relevance);
        c.setFill(color);
    }

    public Color getId()
    {
        return id;
    }

    public void draw()
    {
        updateRelevance();
        c.setRadius(radius);
        c.setTranslateX(pos.getX());
        c.setTranslateY(pos.getY());
    }

    public State getState()
    {
        return state;
    }
}
