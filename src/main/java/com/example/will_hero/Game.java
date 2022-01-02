package com.example.will_hero;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.util.Date;

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

    public void loseGame(){
        animationTimer.stop();
        currentState.disableGamePane();
        if (currentState.hasRevived) {
            gameController.openLosePane();
        }
        else{
            gameController.openRevivePane();
        }
    }

    public void saveGame(){
        try {
            serialise();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void winGame(){
        animationTimer.stop();
        currentState.disableGamePane();
        gameController.openWinPane();
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

    public void revive(){
        gameController.closeRevivePane();
        if (currentState.getCoins() >=40){
            currentState.hasRevived = true;
            currentState.hasEnded = false;
            currentState.setCoins(currentState.getCoins() - 40);
            hero.node.setLayoutY(100);
            hero.setSpeedY(0);
            animationTimer.start();
        }
        else {
            gameController.openUnablePane();
        }
    }

    public void startGame(MouseEvent event){
        this.viewScene(event);
        this.setupFXMLNodes();

        //adding the first island and placing hero on it
        Island first = Island.createIsland(Island.paths[0]);
        first.node.setId("island0");
        currentState.addIsland(first);
        hero.getNode().setLayoutX(215);
        System.out.println((int) hero.getNode().getLayoutX());
        currentState.setHeroStart((int) hero.getNode().getLayoutX());
        hero.getNode().setLayoutY(GameState.getBoundswrtPane(first.getPlatformNode()).getMinY() - hero.HEIGHT);

        for (int i = 0; i < 5; i++) {
            currentState.addALevel();
        }

        animationTimer.start();

//        winGame();
    }



    public void pauseGame(){
        animationTimer.stop();
        currentState.disableGamePane();
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

    public void serialise() throws IOException{
        currentState.prepareSerialization();
        ObjectOutputStream out = null;
        try {
            Date d = new Date();
            out = new ObjectOutputStream(new FileOutputStream("gamestate" + d.toString() + ".txt"));
            out.writeObject(currentState);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally {
            out.close();
        }
    }

    public GameState deserialize(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        GameState state = null;
        try {
            in = new ObjectInputStream(new FileInputStream(path));
            state = (GameState) in.readObject();
        }
        finally {
            in.close();
        }
        return state;
    }

    public void loadGame(MouseEvent event, String path){
        this.viewScene(event);
        try {
            currentState = deserialize(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.gameController.setupFXMLNodes(this, currentState);
        currentState.setGame(this);
        currentState.afterDeserialization();
        this.hero = currentState.getHero();
        setAnimationTimer();
        animationTimer.start();
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
