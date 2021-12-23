package com.example.will_hero;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;

public class Hero extends GameObjects{
    public static final String path = "AssetFXMLFiles/Hero.fxml";
    private final int jumpY = 100;
    private final int jumpTIme = 500;
    private final int forwardX = 100;
    private final int forwardTime = 200;

    private final Helmet helmet;

    public Hero(Node node) {
        super(node);
        helmet = new Helmet();
    }

    public void jump() {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(node);
        transition.setDuration(Duration.millis(this.jumpTIme));
        transition.setByY(-this.jumpY);
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        transition.play();
    }

    public TranslateTransition moveForward() {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(node);
        transition.setDuration(Duration.millis(this.forwardTime));
        transition.setByX(this.forwardX);
        return transition;
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }
}

class Helmet {
    private ArrayList<Weapons> WEAPONS_OPTIONS;
    public Helmet(){
        WEAPONS_OPTIONS = new ArrayList<>();
        WEAPONS_OPTIONS.add(new Weapon1());
        WEAPONS_OPTIONS.add(new Weapon2());
    }
    public Weapons getWeapon(){
        return null;
    }
}