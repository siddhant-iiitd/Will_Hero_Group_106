package com.example.will_hero;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML private AnchorPane scenePane;
    @FXML private ImageView newGameButton;
    @FXML private ImageView loadGameButton;
    @FXML private ImageView exitGameButton;

    @FXML private AnchorPane loadMenu;
    @FXML private Text loadText;
    @FXML private Text cancelText;
    @FXML private ImageView closeMenuButton;
    @FXML private ListView games;



    private Stage stage;
    private Scene scene;
    private Parent root;

    public void exitGame(MouseEvent event) {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void openLoadGame(MouseEvent event) throws IOException {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(loadMenu);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(-516);
        transition.play();
    }

    public void cancelLoadGame(MouseEvent event) throws IOException {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(loadMenu);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(516);
        transition.play();
    }



    public void newGame(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RunningGame.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
