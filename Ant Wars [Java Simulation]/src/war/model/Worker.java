package war.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Worker extends Ant
{
    public static ArrayList<Pheromone> pheromones = new ArrayList<>();

    private Position queenPos;
    private int moveTick;
    private int pheromoneTick;

    public Worker(State state, Pane world, Queen queen)
    {
        super(state, world, queen);
        setPerceptualRange(100);
        setAttackRange(5);
        queenPos = queen.getPos();
        moveTick = rand.nextInt(5);
        pheromoneTick = 0;
    }

    public Position getQueenPos()
    {
        return queenPos;
    }

    public void move()
    {
        moveTick++;
        if (getState() == State.RETURN)
        {
            pheromoneTick++;
            if (pheromoneTick == 5)
            {
                createPheromone(State.RESOURCE_NEARBY);
                pheromoneTick = 0;
            }
            setHeading(new Heading(getPos(), getQueenPos()));
        }

        getPos().move(getHeading(), getWorld(), getRadius());
        if (moveTick % 10 == 0)
        {
            setHeading(new Heading());
        }
    }

    public void collisionCheck(Object other)
    {
        if (other instanceof Queen)
        {
            collisionCheck((Queen) other);
        }
        else if (other instanceof Worker)
        {
            collisionCheck((Worker) other);
        }
        else if (other instanceof Resource)
        {
            collisionCheck((Resource) other);
        }
        else if (other instanceof Pheromone)
        {
            collisionCheck((Pheromone) other);
        }
    }

    public void collisionCheck(Queen other)
    {
        if (getPos().collision(other.getPos(), other.getRadius()))
        {
            // Check if the queen is their queen
            if (other.getId() == getId())
            {
                if (getState() == State.RETURN)
                {
                    other.setResources(other.getResources() + getResources());
                    setResources(0);
                    setState(State.WANDER);
                    setHeading(new Heading());
                }
            }
            else
            {
                createPheromone(State.ENEMY_QUEEN_NEARBY);
                other.setHealth(other.getHealth() - 1);
                other.setState(State.RECOVERING);
                setHealth(0);
            }
        }
    }

    public void collisionCheck(Worker other)
    {
        if (getPos().collision(other.getPos(), other.getRadius()))
        {
            // Check if the worker is not from the same colony
            if (other.getId() != getId() && other.getHealth() > 0 && getHealth() > 0)
            {
                createPheromone(State.ENEMY_NEARBY);
                int winner = rand.nextInt(2);
                if (winner == 0)
                {
                    setHealth(0);
                }
                else
                {
                    other.setHealth(0);
                }
                setState(State.WANDER);
                other.setState(State.WANDER);
            }
        }
    }

    public void collisionCheck(Resource other)
    {
        if (getPos().collision(other.getPos(), other.getSize()))
        {
            createPheromone(State.RESOURCE_NEARBY);
            if (getState() == State.WANDER)
            {
                other.setQuantity(other.getQuantity() - 1);
                other.updateSize();
                setState(State.RETURN);
                setHeading(new Heading(getPos(), getQueenPos()));
                setResources(getResources() + 1);
            }
        }
    }

    public void collisionCheck(Pheromone other)
    {
        if (getPos().collision(other.getPos(), other.getRadius()))
        {
            if (other.getState() == State.RESOURCE_NEARBY && getState() == State.RETURN)
            {
                other.restoreRelevance();
            }
        }
    }

    public void withinPerception(Object other)
    {
        if (other instanceof Ant)
        {
            withinPerception((Ant) other);
        }
        else if (other instanceof Resource)
        {
            withinPerception((Resource) other);
        }
        else if (other instanceof Pheromone)
        {
            withinPerception((Pheromone) other);
        }
    }

    public void withinPerception(Ant other)
    {
        if (getPos().collision(other.getPos(), getRadius() + getPerceptualRange()) && getId() != other.getId() && other.getHealth() > 0 && getState() != State.RETURN)
        {
            setState(State.ATTACK);
            if (other instanceof Worker)
            {
                setState(State.ATTACK);
            }
            setHeading(new Heading(getPos(), other.getPos()));
        }
        else
        {
            if (getState() != State.RETURN && other.getHealth() <= 0)
            {
                setState(State.WANDER);
            }
        }
    }

    public void withinPerception(Resource other)
    {
        if (getPos().collision(other.getPos(), getRadius() + getPerceptualRange()) && other.getQuantity() > 0 && getState() != State.ATTACK)
        {
            setHeading(new Heading(getPos(), other.getPos()));
        }
    }

    public void withinPerception(Pheromone other)
    {
        if (getPos().collision(other.getPos(), getRadius() + getPerceptualRange()) && getId() == other.getId())
        {
            setHeading(new Heading(getPos(), other.getPos()));
        }
    }

    public void createPheromone(State state)
    {
        if (state == State.RESOURCE_NEARBY)
        {
            pheromones.add(new Pheromone(getWorld(), state, getPos(), getId(), 15));
        }
        else if (state == State.ENEMY_NEARBY)
        {
            pheromones.add(new Pheromone(getWorld(), state, getPos(), getId(), 40));
        }
        else if (state == State.ENEMY_QUEEN_NEARBY)
        {
            pheromones.add(new Pheromone(getWorld(), state, getPos(), getId(), 100));
        }
    }
}
