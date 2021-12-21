package com.example.will_hero;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Game{
    private GameController gameController;
    private GameState currentState;


    private Hero hero;

    private Stage stage;
    private Scene scene;

    public void viewScene(MouseEvent event) {
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(WillHeroApplication.class.getResource("Game.fxml"));

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameController = fxmlLoader.getController();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setupFXMLNodes(){
        this.gameController.setupFXMLNodes(this, currentState);
    }

    public Game() {
        currentState = new GameState();
    }


    public void startGame(MouseEvent event) {
        this.viewScene(event);
        this.setupFXMLNodes();

        //adding the first island
        Island first = Island.createIsland(Island.paths[0]);
        currentState.addIsland(first);
        for (int i = 0; i < 5; i++) {
            currentState.addIsland();
        }
    }
}
