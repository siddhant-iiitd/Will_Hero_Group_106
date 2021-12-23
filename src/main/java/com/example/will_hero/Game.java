package com.example.will_hero;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
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


    public Hero hero;

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
        currentState = new GameState(this);
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

        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        System.out.println("hero bounds");
        printBounds(heroBounds);
        System.out.println("island bounds");

        Bounds islandBounds = GameState.getBoundswrtPane(first.getPlatformNode());

        printBounds(islandBounds);

        System.out.println(heroBounds.intersects(islandBounds));

    }

    public static void printBounds(Bounds heroBounds) {
        System.out.println("height " + heroBounds.getHeight());
        System.out.println("widht " + heroBounds.getWidth());
        System.out.println("center x " + heroBounds.getCenterX());
        System.out.println("center y" + heroBounds.getCenterY());
        System.out.println("max x " + heroBounds.getMaxX());
        System.out.println("max y" + heroBounds.getMaxY());
        System.out.println("min x" + heroBounds.getMinX());
        System.out.println("min y" + heroBounds.getMinY());
    }
}
