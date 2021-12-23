package com.example.will_hero;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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


    //FXML Objects
    protected static AnchorPane gamePane;
    private Hero hero;
    private Text scoreBoard;
    private Text coinBoard;

    public GameState(Game game) {
        this.game = game;
    }

    public static Bounds getBoundswrtPane(Node node) {
        return gamePane.sceneToLocal(node.localToScene(node.getBoundsInLocal()));
    }

    public void addObject() {

        Group islandGroup = groupLoader(Island.paths[0]);
        islandGroup.setLayoutX(300);
        islandGroup.setLayoutY(317 - islandGroup.getChildren().get(1).getBoundsInLocal().getHeight());

        Text t1 = new Text();
        t1.setText("Hello");
        t1.setFont(Font.font("Arial"));
        t1.setX(100);
        t1.setY(100);
        gamePane.getChildren().add(t1);
        gamePane.getChildren().add(islandGroup);
    }



    public void addIsland(Island island) {
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
    }
    public void addIsland(){
        addIsland(Island.createIsland());
    }

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


    public void setupFXMLNodes(AnchorPane anchorPane, Text scoreBoard, Text coinBoard, ImageView heroNode) {
        this.hero = new Hero(heroNode);
        this.scoreBoard = scoreBoard;
        this.coinBoard = coinBoard;
        this.gamePane = anchorPane;
        game.hero = new Hero(heroNode);
    }

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
