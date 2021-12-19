package com.example.will_hero;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GameController {
    private Game game;
    @FXML private AnchorPane gamePane;
    @FXML private ImageView hero;
    @FXML private Text scoreBoard;
    @FXML private Text coinBoard;
    @FXML private ImageView pauseButton;


    public void PauseButtonClicked(MouseEvent Event) {

    }

    public void setupFXMLNodes(Game game, GameState gameState){
        this.game = game;
        gameState.setupFXMLNodes(gamePane, scoreBoard, coinBoard, hero);
    }

    public GameController() {

    }
}
