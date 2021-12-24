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

    volatile private double speedY = 0;
    volatile private double speedX = 0;

    private final Helmet helmet;

    public Hero(Node node) {
        super(node);
        helmet = new Helmet();
    }

    // vertical movement to be called for each frame
    public void moveFrameWise(){
        double displacement = -Physics.dispGravSecond(this.speedY);
        double finalSpeed = Physics.velocityChangeDownwards(this.speedY);
//        node.setTranslateY(displacement * 10);
//        System.out.println("moving frame current speed " + this.speedY + " displacement: " + displacement + " final speed: " + finalSpeed);
//        System.out.println("current y position: " + node.getBoundsInParent().getMaxY());
//        this.speedY = finalSpeed;
        System.out.println("speed before" + this.speedY + " y position before " + node.getBoundsInParent().getMaxY());
        node.setLayoutY(node.getLayoutY() + displacement);

        //this.speedY = this.speedY + Physics.gravity;
        this.speedY = finalSpeed;
        System.out.println("speed now " + this.speedY + " y position after " + node.getBoundsInParent().getMaxY());
    }

    public void jump() {
        System.out.println("collision happened and set new speed");
        this.speedY = 5;
        System.out.println("current speed " + this.speedY);
//        TranslateTransition transition = new TranslateTransition();
//        transition.setNode(node);
//        transition.setDuration(Duration.millis(this.jumpTIme));
//        transition.setByY(-this.jumpY);
//        transition.setAutoReverse(true);
//        transition.setCycleCount(2);
//        transition.play();
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