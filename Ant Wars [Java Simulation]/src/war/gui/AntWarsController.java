package war.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import war.model.Queen;
import war.model.Resource;
import war.model.Simulation;
import war.model.Worker;

import java.util.ArrayList;

public class AntWarsController
{
    @FXML
    Pane world;

    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    Button stepButton;

    @FXML
    TextField tickText;

    Simulation sim;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private Movement clock;

    private class Movement extends AnimationTimer
    {
        private long FRAMES_PER_SEC = 50L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;

        private long last = 0;
        private int ticks = 0;

        @Override
        public void handle(long now)
        {
            if (now - last > INTERVAL)
            {
                step();
                last = now;
            }
        }

        public int getTicks()
        {
            return ticks;
        }

        public void resetTicks()
        {
            ticks = 0;
        }

        public void tick()
        {
            ticks++;
        }
    }

    @FXML
    public void initialize()
    {
        clock = new Movement();
        world.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }

    @FXML
    public void reset()
    {
        stop();
        clock.resetTicks();
        tickText.setText("" + clock.getTicks());
        world.getChildren().clear();
        Queen.queens = 0;
        Resource.resourceQuantity = 0;
        Worker.pheromones = new ArrayList<>();
        sim = new Simulation(world, 2);
    }

    @FXML
    public void start()
    {
        if (clock.getTicks() > 1)
        {
            if (Queen.queens < 2 || sim.onlyOneLivingColony())
            {
                endSimulation();
            }
        }

        clock.start();
        disableButtons(true, false, true);
    }

    @FXML
    public void stop()
    {
        clock.stop();
        disableButtons(false, true, false);
    }

    @FXML
    public void step()
    {
        sim.setPheromones(Worker.pheromones);
        sim.move();
        sim.resolveCollisions();
        sim.spawnAnt();
        sim.draw();
        clock.tick();
        tickText.setText("" + clock.getTicks());

        while (Resource.resourceQuantity < 5)
        {
            sim.spawnResource();
        }

        if (Queen.queens < 2 || sim.onlyOneLivingColony())
        {
            endSimulation();
        }
    }

    public void disableButtons(boolean start, boolean stop, boolean step)
    {
        startButton.setDisable(start);
        stopButton.setDisable(stop);
        stepButton.setDisable(step);
    }

    public void endSimulation()
    {
        clock.stop();
        disableButtons(true, true, true);
        alert.setHeaderText("The simulation has ended.");
        alert.show();
    }
}
