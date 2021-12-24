package com.example.will_hero;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameState {
    private final Game game;

    Random rand = new Random();

    private ArrayList<GameObjects> gameObjects = new ArrayList<>();
    private ArrayList<Enemies> enemies = new ArrayList<>();
    private ArrayList<Island> islands = new ArrayList<>();
    private ArrayList<TNT> tnts = new ArrayList<>();
    private ArrayList<Chests> chests = new ArrayList<>();
    private int steps;
    private int coins;
    private boolean hasEnded;
    private boolean hasRevived;

    private long lastClicked = 0;
    private double toMoveFrameX = 0;

    //FXML Objects
    protected static AnchorPane gamePane;
    private Hero hero;
    private Text scoreBoard;
    private Text coinBoard;

    public GameState(Game game) {
        this.game = game;
    }

    public Hero addHero(){
        ImageView heroNode = imageViewLoader(Hero.path);
        Hero h = new Hero(heroNode);
        gamePane.getChildren().add(heroNode);
        gameObjects.add(h);
        hero = h;
        return h;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
    public Hero getHero() {
        return this.hero;
    }

    // method which runs for each frame in animation timer
    public void updateState(long now) {
        System.out.println(now);
        gamePane.setOnMouseClicked(event -> {
            hero.setToMoveX(hero.getToMoveX() + 90);
            toMoveFrameX += 90;
            this.lastClicked = now;
//            TranslateTransition t1 = hero.moveForward();
//            t1.setOnFinished(actionEvent -> {
//                moveSceneBackwards(100, 200);
//            });
//            t1.play();
        });


        hero.node.toFront();
        if (checkCollisionWithIslands()){
            hero.jump();
        }

        moveFrameBack(now, 6);
        hero.moveFrameWise();

        //movement of the frame

        for (Enemies e : enemies) {
            //add the jumping thing
        }
    }

    public void moveFrameBack(long now, double x){
        if (!(toMoveFrameX > 100 || (now - lastClicked > 200000000))) {
            return;
        }
        double toMove = (x < toMoveFrameX) ? x : toMoveFrameX;
        toMoveFrameX -= toMove;
        for (GameObjects object : gameObjects) {
            Node node = object.getNode();
            node.setLayoutX(node.getLayoutX() - toMove);
        }
    }

    //checking if hero is on any of the islands
    private boolean checkCollisionWithIslands(){
        for(Island i : islands) {
            if (i.isColliding(hero)){
                return true;
            }
        }
        return false;
    }

    // helper function to get the bounds of any node with respect to the scene pane
    public static Bounds getBoundswrtPane(Node node) {
        return gamePane.sceneToLocal(node.localToScene(node.getBoundsInLocal()));
    }

    //Helper function to add the object to the gameObjects list too. Must call this function whenever
    //adding any object such as island to the specific lists
    private void addGameObject(GameObjects object){
        gameObjects.add(object);
    }

    //function to add a given island to the islands list
    public Island addIsland(Island island) {
        island.getNode().setLayoutY(170 - rand.nextInt(30));
        if (islands.size() < 1) {
            island.getNode().setLayoutX(5);
        }
        else {
            Island prevIsland = islands.get(islands.size() -1 );
            island.getNode().setLayoutX(prevIsland.getNode().getLayoutX() + rand.nextInt(100) + 75 + prevIsland.WIDTH);
        }
        islands.add(island);

        gamePane.getChildren().add(island.getNode());
        if (islands.size() > 7) {

            Island removedIsland = islands.get(0);
            this.removeObject(removedIsland);
        }
        addGameObject(island);
        return island;
    }

    //function to add random new island to islands list
    public Island addIsland(){
        return addIsland(Island.createIsland());
    }

    //function to remove a gameObject from all lists containing that object
    public void removeObject(GameObjects object){
        gamePane.getChildren().remove(object.getNode());
        if (object instanceof Enemies) {
            enemies.remove(object);
        }
        else if (object instanceof Island) {
            islands.remove(object);
        }
        else if (object instanceof Chests) {
            chests.remove(object);
        }
        else if (object instanceof TNT) {
            tnts.remove(tnts);
        }
        gameObjects.remove(object);
    }

    //function for moving all objects in the game backwards by x in time t
    public void moveSceneBackwards(double x, double t){
        for (GameObjects object : gameObjects) {
            Node node = object.getNode();
            TranslateTransition transition = new TranslateTransition(Duration.millis(t), node);
            transition.setByX(-x);
            transition.play();
        }
    }


    //function for initial setup of some required static gui component nodes
    public void setupFXMLNodes(AnchorPane anchorPane, Text scoreBoard, Text coinBoard) {

        this.scoreBoard = scoreBoard;
        this.coinBoard = coinBoard;
        this.gamePane = anchorPane;
    }

    public void enableForward(){
        gamePane.setOnMouseClicked(event -> {
            hero.setToMoveX(hero.getToMoveX() + 100);
            toMoveFrameX += 100;

//            TranslateTransition t1 = hero.moveForward();
//            t1.setOnFinished(actionEvent -> {
//                moveSceneBackwards(100, 200);
//            });
//            t1.play();
        });
    }
    //helper static function to load a group from fxml file with given path
    public static Group groupLoader(String path) {
        AnchorPane root = null;
        try {
            root = FXMLLoader.<AnchorPane>load(WillHeroApplication.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Group group = (Group) root.getChildren().get(0);
        return group;
    }

    //helper function to load imageview from fxml file with given path
    public static ImageView imageViewLoader(String path) {
        AnchorPane root = null;
        try {
            root = FXMLLoader.<AnchorPane>load(WillHeroApplication.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView image = (ImageView) root.getChildren().get(0);
        return image;
    }
}
