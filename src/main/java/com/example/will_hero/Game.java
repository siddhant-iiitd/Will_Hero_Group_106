package com.example.will_hero;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game{
    private GameController gameController;
    private GameState currentState;


    private Hero hero;

    private Stage stage;
    private Scene scene;

    public void viewScene() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RunningGame.fxml"));
            scene = new Scene(loader.load());
            stage.setScene(scene);
            gameController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Game() {
    }
}
