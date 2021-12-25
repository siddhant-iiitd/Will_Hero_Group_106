package com.example.will_hero;

import javafx.animation.AnimationTimer;
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
    private AnimationTimer animationTimer;

    public Hero hero;

    private Stage stage;
    private Scene scene;

    public Game() {
        currentState = new GameState(this);
        setAnimationTimer();
    }

    public GameState getCurrentState(){
        return this.currentState;
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
        //currentState.enableForward();

        //adding the first island
        Island first = Island.createIsland(Island.paths[0]);
        currentState.addIsland(first);
        hero.getNode().setLayoutX(215);
        hero.getNode().setLayoutY(GameState.getBoundswrtPane(first.getPlatformNode()).getMinY() - hero.HEIGHT);
        hero.getNode().toFront();

        for (int i = 0; i < 5; i++) {
            Island is = currentState.addIsland();
            Bounds islandBounds = GameState.getBoundswrtPane(is.getPlatformNode());
            Enemies enemy = currentState.addEnemy();
            enemy.getNode().setLayoutX(islandBounds.getCenterX());
            enemy.getNode().setLayoutY(islandBounds.getMinY() - enemy.HEIGHT);
        }

        Chests c1 = currentState.addChests();

        Bounds c1Bound = GameState.getBoundswrtPane(c1.node);
        Bounds firstbound = GameState.getBoundswrtPane(first.getPlatformNode());
        printBounds(c1Bound);
        printBounds(first.islandBounds());
        c1.node.setLayoutX(firstbound.getCenterX() - c1.WIDTH/2);
        c1.node.setLayoutY(firstbound.getMinY()-c1.HEIGHT); //top of island
        //c1.node.setLayoutY(first.islandBounds().getCenterY());

        animationTimer.start();

    }

    public void pauseGame(){

        animationTimer.stop();
    }
    public void resumeGame(){
        animationTimer.start();

    }

    //helper method to set up the animation timer which runs frame by frame
    private void setAnimationTimer() {
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                currentState.updateState(now);
            }
        };
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
