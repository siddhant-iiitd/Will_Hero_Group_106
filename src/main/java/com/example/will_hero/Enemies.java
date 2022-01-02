package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public abstract class Enemies extends GameObjects {
    protected double speedX = 5;
    protected double speedY = 0;
    protected double toMoveX = 0;
    private int delayY = 0;
    protected long lastLanded = Long.MAX_VALUE;
    public boolean onIsland = false;
    public boolean isKilled = false;

    protected Enemies(Node node) {
        super(node);
    }

    public String getPath(){
        return this.getPath();
    }

    @Override
    public Boolean isColliding(Hero hero){
        Bounds enemyBounds = GameState.getBoundswrtPane(node);
        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        if (enemyBounds.intersects(heroBounds)) {
//            if (heroBounds.getMaxX() > enemyBounds.getMinX() + hero.getSpeedX() && heroBounds.getMinY() <= enemyBounds.getMaxY() + Math.max(speedY, hero.getSpeedY())){
//                killHero(hero);
//                return true;
//            }
            //collision from bottom
            double difference = enemyBounds.getMaxY() - heroBounds.getMinY();
            if (difference < 5){
                killHero(hero);
                return true;
            }
            //collision from the top
            //difference = enemyBounds.getMinY();
//            if(enemyBounds.getMinY() - Math.abs(this.speedY) <= heroBounds.getMaxY() + Math.abs(hero.getSpeedY())){
              if (heroBounds.getMaxY() - enemyBounds.getMinY() < 5){
                if (this.speedY < 0) {
                    hero.setSpeedY(0);
                }
                else {
                    hero.setSpeedY(4);
                }
                System.out.println("collide with enemy on top");
                return false;
            }
            hero.collideEnemy();
            collide();
            return false;
        }
        return false;
    }

    public void killed(){
        System.out.println("enemy is killed");
        this.isKilled = true;
    }

    public void collide(){
        toMoveX = 100;
    }

    public void killHero(Hero hero){
        System.out.println("hero is killed");
    }

    public void moveFrameWise(){
        if (toMoveX > 0){
            double moveX = Math.min(speedX, toMoveX);
            toMoveX -= moveX;
            node.setLayoutX(node.getLayoutX() + moveX);
            return;
        }
        if (!onIsland) {
            double displacement = -Physics.dispGravSecond(this.speedY);
            double finalSpeed = Physics.velocityChangeDownwards(this.speedY);
            node.setLayoutY(node.getLayoutY() + displacement);
            this.speedY = finalSpeed;
            return;
        }
    }

    public void jump(long now){
        if (now - lastLanded > 100000000) {
            lastLanded = Long.MAX_VALUE;
            onIsland = false;
            this.speedY = 6;
            return;
        }
        onIsland = true;
        lastLanded = Math.min(now, lastLanded);
    }

    public double getSpeedY() {
        return speedY;
    }
}

class RedOrc extends Enemies {
    public static final String path = "AssetFXMLFiles/RedOrc.fxml";

    public RedOrc(Node node) {
        super(node);
    }

//    @Override
//    public Boolean isColliding(Hero hero) {
//        return null;
//    }
}

class GreenOrc extends Enemies {
    public static final String path = "AssetFXMLFiles/GreenOrc.fxml";


    public GreenOrc(Node node) {
        super(node);
    }

//    @Override
//    public Boolean isColliding(Hero hero) {
//        return null;
//    }
}

class Boss extends Enemies {
    private int hits = 0;
    public static final String path = "AssetFXMLFiles/Boss.fxml";

    public Boss(Node node) {
        super(node);
        speedY = 3;
        speedX = 3;
    }

    @Override
    public void jump(long now){
        if (now - lastLanded > 100000000) {
            lastLanded = Long.MAX_VALUE;
            onIsland = false;
            this.speedY = 3;
            return;
        }
        onIsland = true;
        lastLanded = Math.min(now, lastLanded);
    }


    public void collide(){
        hits += 1;
        if (hits >= 10) {
            killed();
        }
        toMoveX = 10;

    }
    public void hit(){
        hits+=1;
        if (hits >= 10) {
            killed();
        }
    }
    @Override
    public Boolean isColliding(Hero hero){
        Bounds enemyBounds = GameState.getBoundswrtPane(node);
        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        if (enemyBounds.intersects(heroBounds)) {
            //collision from bottom
            double difference = enemyBounds.getMaxY() - heroBounds.getMinY();
            if (difference < 5){
                killHero(hero);
                return true;
            }

            hero.collideEnemy();
            collide();
            return false;
        }
        return false;
    }


//
//    @Override
//    public Boolean isColliding(Hero hero) {
//        return false;
//    }
}