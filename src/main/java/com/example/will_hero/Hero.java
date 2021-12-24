package com.example.will_hero;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;

public class Hero extends GameObjects{
    public static final String path = "AssetFXMLFiles/Hero.fxml";
    public static final int forwardX = 120;
    private final int jumpY = 100;
    private final int jumpTIme = 500;
    private final int forwardTime = 200;

    volatile private double speedY = 0;
    volatile private double speedX = 8;

    volatile private double toMoveX = 0;

    private final Helmet helmet;

    public Hero(Node node) {
        super(node);
        helmet = new Helmet();
    }

    public double getSpeedY(){
        return this.speedY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getToMoveX(){
        return toMoveX;
    }

    public void setToMoveX(double toMoveX) {
        this.toMoveX = toMoveX;
    }

    // vertical movement to be called for each frame
    public void moveFrameWise(){
        if (toMoveX > 0){
            double moveX = (speedX < toMoveX) ? speedX : toMoveX;
            toMoveX -= moveX;
            node.setLayoutX(node.getLayoutX() + moveX);
            return;
        }
        double displacement = -Physics.dispGravSecond(this.speedY);
        double finalSpeed = Physics.velocityChangeDownwards(this.speedY);
        node.setLayoutY(node.getLayoutY() + displacement);
        this.speedY = finalSpeed;
        return;
    }

    public void jump() {
        this.speedY = 5.0;
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