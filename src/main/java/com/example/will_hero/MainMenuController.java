package com.example.will_hero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML private AnchorPane scenePane;
    @FXML private Button newGameButton;
    @FXML private Button loadGameButton;
    @FXML private Button exitGameButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void exitGame(ActionEvent event) {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void loadGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("load-game-menu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void newGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Game_is_running_here.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
