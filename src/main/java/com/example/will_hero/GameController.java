package com.example.will_hero;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private Game game;
    @FXML private AnchorPane gamePane;
    @FXML private Text scoreBoard;
    @FXML private Text coinBoard;
    @FXML private Button pauseBtn;


    // Save Menu
    @FXML private AnchorPane savePane;
    @FXML private ImageView closeButtonSavePane;

    //Pause Menu
    @FXML private AnchorPane pauseMenu;
    @FXML private ImageView cross;
    @FXML private ImageView play;
    @FXML private ImageView save;
    @FXML private ImageView home;

    private Stage stage;
    private Scene scene;

    public GameController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setupFXMLNodes(Game game, GameState gameState){
        this.game = game;
        gameState.setupFXMLNodes(gamePane, scoreBoard, coinBoard);
    }



    public void PauseButtonClicked(MouseEvent Event) {

    }


    public void closeSavePane(MouseEvent event){
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(savePane);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(386);
        transition.play();
    }

    public void openSavePane(MouseEvent event) {
        savePane.toFront();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(pauseMenu);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(434);
        transition.play();
        TranslateTransition transition2 = new TranslateTransition();
        transition2.setNode(savePane);
        transition2.setDuration(Duration.millis(1000));
        transition2.setByY(-386);
        transition2.play();
    }


    public void pauseGame(MouseEvent event) throws IOException {
        game.pauseGame();
        pauseMenu.toFront();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(pauseMenu);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(-434);
        transition.play();

    }

    public void playGame(MouseEvent event) throws IOException {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(pauseMenu);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(434);
        transition.play();
        game.resumeGame();
    }

    public void goHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
