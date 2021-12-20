package war.model;

import javafx.scene.layout.Pane;

public class Queen extends Ant
{
    public static int queens = 0;
    public static int totalColonies = 0;
    public static final int spawnCost = 1;

    private boolean alive;

    public Queen(State state, Pane world)
    {
        super(state, world);
        queens++;
        setPerceptualRange(10);
        setAttackRange(getPerceptualRange());
        setMaxHealth(20);
        setHealth(getMaxHealth());
        setResources(15);
        alive = true;
    }

    public void deductResources()
    {
        setResources(getResources() - spawnCost);
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void move() {}

    public void collisionCheck(Object other) {

    }

    public void regenerateHealth()
    {
        setResources(getResources() - 1);
        setHealth(getHealth() + 1);
        if (getHealth() == getMaxHealth())
        {
            setState(State.WANDER);
        }
    }
}
