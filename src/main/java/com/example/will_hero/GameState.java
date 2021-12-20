package com.example.will_hero;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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


    public void setupFXMLNodes(AnchorPane anchorPane, Text scoreBoard, Text coinBoard, ImageView heroNode) {
        this.hero = new Hero(heroNode);
        this.scoreBoard = scoreBoard;
        this.coinBoard = coinBoard;
        this.gamePane = anchorPane;
    }
}
