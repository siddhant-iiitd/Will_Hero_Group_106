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

public class PauseMenuController {
    @FXML private Label myLabel;

    @FXML private AnchorPane scenePane;
    @FXML private Button saveProgBtn;
    @FXML private Button rtnToHomePgBtn;
    @FXML private Button resumeGameBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void rtnToHomePg(ActionEvent event) {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
        //link to the main menu
    }

    public void saveProg(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("savedGames.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void resumeGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("savedGames.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




//    @FXML
//    Button btnScene1, btnScene2;
//    public void handleBtn1() throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("Pause_Menu.fxml"));
//        Stage window = (Stage) btnScene1.getScene().getWindow();
//        window.setScene(new Scene(root, 750, 500));
//
//
//
//    }
}
