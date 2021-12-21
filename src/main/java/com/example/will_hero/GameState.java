package com.example.will_hero;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class GameState {

    private ArrayList<GameObjects> gameObjects;
    private ArrayList<Enemies> enemies;
    private ArrayList<Rock> rocks;
    private ArrayList<TNT> tnts;
    private ArrayList<Chests> chests;
    private int steps;
    private int coins;
    private boolean hasEnded;
    private boolean hasRevived;

    //FXML Objects
    private AnchorPane gamePane;
    private Hero hero;
    private Text scoreBoard;
    private Text coinBoard;

    public void addObject() {
        AnchorPane root = null;
        try {
            root = FXMLLoader.<AnchorPane>load(WillHeroApplication.class.getResource("AssetFXMLFiles/Rock2.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Group rockGroup = (Group) root.getChildren().get(0);
        rockGroup.setLayoutX(300);
        rockGroup.setLayoutY(317 - rockGroup.getChildren().get(1).getBoundsInLocal().getHeight());

        Text t1 = new Text();
        t1.setText("Hello");
        t1.setFont(Font.font("Arial"));
        t1.setX(100);
        t1.setY(100);
        gamePane.getChildren().add(t1);
        gamePane.getChildren().add(rockGroup);
    }

    public void setupFXMLNodes(AnchorPane anchorPane, Text scoreBoard, Text coinBoard, ImageView heroNode) {
        this.hero = new Hero(heroNode);
        this.scoreBoard = scoreBoard;
        this.coinBoard = coinBoard;
        this.gamePane = anchorPane;
    }
}
