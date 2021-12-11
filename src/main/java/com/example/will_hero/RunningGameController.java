package com.example.will_hero;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    @FXML private Button pauseBtn;

    @FXML private ImageView redOrc1;
    @FXML private ImageView redOrc2;
    @FXML private ImageView greenOrc1;
    @FXML private ImageView hero;

    @FXML private AnchorPane pauseMenu;
    @FXML private ImageView cross;
    @FXML private ImageView play;
    @FXML private ImageView save;
    @FXML private ImageView home;

    @FXML private AnchorPane savePane;
    @FXML private ImageView closeButtonSavePane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void closeSavePane(MouseEvent event){
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(savePane);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(386);
        transition.play();
    }

    public void openSavePane(MouseEvent event) {
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
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(pauseMenu);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(-434);
        transition.play();
    }

    public void goHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void playGame(MouseEvent event) throws IOException {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(pauseMenu);
        transition.setDuration(Duration.millis(1000));
        transition.setByY(434);
        transition.play();
    }

    public static TranslateTransition forward(Node node, int x, int time) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(node);
        transition.setDuration(Duration.millis(time));
        transition.setByX(x);
        return transition;
    }

    public void heroForward(Event event) {
        TranslateTransition transition = forward(hero, 100, 300);
        transition.play();
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
