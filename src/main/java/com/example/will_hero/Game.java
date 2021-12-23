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

    public Game() {
        currentState = new GameState(this);
    }

    //helper function to view the game scene
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

    //helper function to setup fxml nodes from the fxml file
    public void setupFXMLNodes(){
        this.gameController.setupFXMLNodes(this, currentState);
        this.hero = currentState.addHero();
    }



    // function executed on starting a game
    public void startGame(MouseEvent event) {
        this.viewScene(event);
        this.setupFXMLNodes();

        //adding the first island
        Island first = Island.createIsland(Island.paths[0]);
        currentState.addIsland(first);
        hero.getNode().setLayoutX(215);
        hero.getNode().setLayoutY(GameState.getBoundswrtPane(first.getPlatformNode()).getMinY() - hero.HEIGHT);
        hero.getNode().toFront();
//        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
//        System.out.println("hero bounds");
//        printBounds(heroBounds);
//        System.out.println("first island bounds");
//        Bounds islandBounds = GameState.getBoundswrtPane(first.getPlatformNode());
//        printBounds(islandBounds);

        for (int i = 0; i < 5; i++) {
            Island is = currentState.addIsland();
//            System.out.println("bounds for island " + i);
//            Bounds b = GameState.getBoundswrtPane(is.getPlatformNode());
//            printBounds(b);
        }

        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        System.out.println("hero bounds");
        printBounds(heroBounds);
        System.out.println("first island bounds");
        Bounds islandBounds = GameState.getBoundswrtPane(first.getPlatformNode());
        printBounds(islandBounds);
        System.out.println(heroBounds.intersects(islandBounds));
        System.out.println(first.getPlatformNode().getClass());


        if (first.isColliding(hero)) {
            System.out.println("jump");
        }
        else {
            System.out.println("no jump");

        }

       // currentState.moveSceneBackwards(100, 300);



    }

    //function to print the bounds mainly for debugging
    private static void printBounds(Bounds heroBounds) {
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
