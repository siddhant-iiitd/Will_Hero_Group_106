package com.example.will_hero;

import javafx.scene.Node;

import java.util.ArrayList;

public class Hero extends GameObjects{
    private final Game game;
    public static final String path = "AssetFXMLFiles/Hero.fxml";
    public static final int forwardX = 120;
    private final int jumpY = 100;
    private final int jumpTIme = 500;
    private final int forwardTime = 200;
    public boolean onIsland = false;
    private long lastLanded = Long.MAX_VALUE;

    private double speedY = 0;
    private double speedX = 8;

    private double toMoveX = 0;

    private final Helmet helmet;

    public Hero(Node node, Game game) {
        super(node);
        this.game = game;
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
            double moveX = speedX;
            if (moveX > Math.abs(toMoveX)) {
                moveX = Math.abs(toMoveX);
            }
            toMoveX -= moveX;
            node.setLayoutX(node.getLayoutX() + moveX);
            return;
        }
        if (toMoveX < 0) {
            double moveX = 3;
            if (moveX > Math.abs(toMoveX)) {
                moveX = Math.abs(toMoveX);
            }
            toMoveX += moveX;
            node.setLayoutX(node.getLayoutX() - moveX);
            return;
        }

        if (!onIsland) {
            double displacement = -Physics.dispGravSecond(this.speedY);
            double finalSpeed = Physics.velocityChangeDownwards(this.speedY);
            node.setLayoutY(node.getLayoutY() + displacement);
            this.speedY = finalSpeed;
        }
        return;
    }


    public void jump(long now) {
        if (now - lastLanded > 100000000) {
            lastLanded = Long.MAX_VALUE;
            onIsland = false;
            this.speedY = 5;
            return;
        }
        onIsland = true;
        lastLanded = Math.min(now, lastLanded);
    }

    public void collideEnemy(){
        game.getCurrentState().toMoveFrameX -=  this.toMoveX + 50;
        this.toMoveX = -50;
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }

    public void collideEnemyTop(double speed) {
        this.speedY = speed;
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