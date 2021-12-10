package com.example.will_hero;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RunningGameController implements Initializable {
    @FXML private AnchorPane scenePane;
    @FXML private ImageView pauseBtn;

    @FXML private ImageView redOrc1;
    @FXML private ImageView redOrc2;
    @FXML private ImageView greenOrc1;
    @FXML private ImageView hero;


    private Stage stage;
    private Scene scene;
    private Parent root;


    public void pauseGame(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pause_Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static TranslateTransition jumping(Node node,  int y, int time) {

        TranslateTransition transition = new TranslateTransition();
        transition.setNode(node);
        transition.setDuration(Duration.millis(time));
        transition.setByY(y);
        return transition;
    }

    public static SequentialTransition jumpWithPause(Node node, int y, int time, int pause) {
        TranslateTransition t1 = jumping(node, -y, time);
        TranslateTransition t2 = jumping(node, y, time);
        SequentialTransition seq = new SequentialTransition(t1, t2, new PauseTransition(Duration.millis(pause)));
        seq.setCycleCount(Transition.INDEFINITE);
        return  seq;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SequentialTransition s1 = jumpWithPause(redOrc1, 80, 510, 200);
        s1.play();

        SequentialTransition s2 = jumpWithPause(redOrc2, 80, 510, 200);
        s2.play();

        SequentialTransition s3 = jumpWithPause(greenOrc1, 70, 490, 170);
        s3.play();

        SequentialTransition s4 = jumpWithPause(hero, 65, 475, 210);
        s4.play();
    }
}
