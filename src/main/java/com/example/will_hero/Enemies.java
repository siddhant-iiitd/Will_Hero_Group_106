package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public abstract class Enemies extends GameObjects {
    private double speedX = 5;
    private double speedY = 0;
    private double toMoveX = 0;
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
            hero.collideEnemy();
            collide();
            return true;
        }
        return false;
    }

    private void collide(){
        toMoveX = 50;
    }

    private void killHero(Hero hero){
        System.out.println("hero is killed");
    }

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

    public void jump(){
        this.speedY = 6;
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