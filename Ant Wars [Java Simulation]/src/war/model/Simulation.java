package war.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;

public class Simulation
{
    private ArrayList<Ant> ants;
    private ArrayList<Queen> queens;
    private ArrayList<Resource> resources;
    private ArrayList<Object> objects;
    private ArrayList<Pheromone> pheromones;
    private final int initialResources = 5;
    private Pane world;

    public Simulation(Pane world, int colonyQuantity)
    {
        this.world = world;
        ants = new ArrayList<>();
        queens = new ArrayList<>();
        resources = new ArrayList<>();
        objects = new ArrayList<>();
        pheromones = new ArrayList<>();

        Queen.totalColonies = colonyQuantity;
        for (int i = 0; i < colonyQuantity; i++)
        {
            Queen queen = new Queen(State.WANDER, world);
            queens.add(queen);
            ants.add(queen);
            objects.add(queen);
        }

        for (int i = 0; i < initialResources; i++)
        {
            Resource resource = new Resource(world, ants);
            resources.add(resource);
            objects.add(resource);
        }

        draw();
    }

    public boolean onlyOneLivingColony()
    {
        int livingColonies = 0;
        for (Queen queen : queens)
        {
            for (Ant ant : ants)
            {
                if (ant != queen && ant.getId() == queen.getId())
                {
                    livingColonies++;
                    break;
                }
            }
        }

        return livingColonies < 2;
    }

    public void move()
    {
        for (Ant ant : ants)
        {
            ant.move();
        }
    }

    public void draw()
    {
        Iterator antIterator = ants.iterator();
        while (antIterator.hasNext())
        {
            Ant ant = (Ant) antIterator.next();
            if (ant.getHealth() > 0)
            {
                ant.draw();
            }
            else
            {
                ant.remove();
                antIterator.remove();
                if (ant instanceof Queen)
                {
                    Iterator queenIterator = queens.iterator();
                    while (queenIterator.hasNext())
                    {
                        Queen queen = (Queen) queenIterator.next();
                        if (queen == ant)
                        {
                            queenIterator.remove();
                            Queen.queens--;
                            break;
                        }
                    }
                }
            }
        }

        Iterator resourceIterator = resources.iterator();
        while (resourceIterator.hasNext())
        {
            Resource resource = (Resource) resourceIterator.next();
            if (resource.getQuantity() > 0)
            {
                resource.draw();
            }
            else
            {
                Resource.resourceQuantity--;
                resource.remove();
                resourceIterator.remove();
            }
        }

        Iterator pheromoneIterator = pheromones.iterator();
        while (pheromoneIterator.hasNext())
        {
            Pheromone pheromone = (Pheromone) pheromoneIterator.next();
            if (pheromone.getRelevance() > 0.02)
            {
                pheromone.draw();
            }
            else
            {
                pheromone.remove();
                pheromoneIterator.remove();
            }
        }
    }

    public void spawnAnt()
    {
        for (Queen queen : queens)
        {
            if (queen.getResources() >= Queen.spawnCost && queen.isAlive() && queen.getHealth() == queen.getMaxHealth())
            {
                Worker worker = new Worker(State.WANDER, world, queen);
                ants.add(worker);
                objects.add(worker);
                queen.deductResources();
            }
            else if (queen.getResources() > 0 && queen.isAlive() && queen.getHealth() < queen.getMaxHealth())
            {
                queen.regenerateHealth();
            }
        }

        draw();
    }

    public void spawnResource()
    {
        Resource resource = new Resource(world, ants);
        resources.add(resource);
        objects.add(resource);
        draw();
    }

    public void resolveCollisions()
    {
        for (Ant ant : ants)
        {
            for (Object object : objects)
            {
                if (ant != object)
                {
                    if (ant instanceof Worker)
                    {
                        ((Worker) ant).withinPerception(object);
                    }
                    ant.collisionCheck(object);
                }
            }

            for (Pheromone pheromone : pheromones)
            {
                ant.collisionCheck(pheromone);
            }
        }
    }

    public void setPheromones(ArrayList<Pheromone> pheromones)
    {
        this.pheromones = pheromones;
    }
}