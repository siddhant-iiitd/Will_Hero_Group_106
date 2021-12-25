package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public abstract class Enemies extends GameObjects {
    private double speedX = 5;
    private double speedY = 0;
    private double toMoveX = 0;
    private int delayY = 0;
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
            //collision from the top
            if(enemyBounds.getMinY() >= heroBounds.getMaxY()){

            }
            hero.collideEnemy();
            collide();
            return true;
        }
        return false;
    }

    private void collide(){
        toMoveX = 100;
    }

    private void killHero(Hero hero){
        System.out.println("hero is killed");
    }

    public void moveFrameWise(){
        if (toMoveX > 0){
            double moveX = Math.min(speedX, toMoveX);
            toMoveX -= moveX;
            node.setLayoutX(node.getLayoutX() + moveX);
            return;
        }
        if (delayY == 0) {
            double displacement = -Physics.dispGravSecond(this.speedY);
            double finalSpeed = Physics.velocityChangeDownwards(this.speedY);
            node.setLayoutY(node.getLayoutY() + displacement);
            this.speedY = finalSpeed;
            return;
        }
    }

    public void jump(){
        if (delayY == 0) {
            delayY = 6;
            return;
        }
        delayY -=1;
        if (delayY <= 1){
            this.speedY = 6.0;
            delayY = 0;
            return;
        }
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
    public static final String path = "AssetFXMLFiles/RedOrc.fxml";


    public GreenOrc(Node node) {
        super(node);
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }
}

class Boss extends Enemies {
    public static final String path = "AssetFXMLFiles/Boss.fxml";

    public Boss(Node node) {
        super(node);
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }
}