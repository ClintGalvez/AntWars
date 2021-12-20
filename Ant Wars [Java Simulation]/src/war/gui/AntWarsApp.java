/*
    Name: Clint Galvez
    Date: 14 December 2020
    Assignment: 1406-Z Individual Course Project (Ant Wars)
    Purpose: To create a simulation of ant colonies warring against each other
 */

package war.gui;

import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class AntWarsApp extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            FXMLLoader loader  = new FXMLLoader();
            BorderPane root = (BorderPane)loader.load(getClass().getResource("AntWarsGUI.fxml").openStream());
            primaryStage.setTitle("Ant Wars");
            primaryStage.setScene(new Scene(root));

            // Set and bind to fullscreen
            primaryStage.setFullScreen(true);
            primaryStage.setResizable(false);
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
