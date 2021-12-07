package com.example.will_hero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameIsRunningHereController {

    @FXML
    private AnchorPane scenePane;
    @FXML
    private Button pauseBtn;


    private Stage stage;
    private Scene scene;
    private Parent root;


    public void pauseBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pause_Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
