package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Weapons extends GameObjects{
    protected double speedX = 10;
    public double toMoveX = 500;
    public boolean killedSomeone = false;

    public Weapons(Node node) {
        super(node);
    }


    public void moveByFrame(){
        double moveX = Math.min(speedX, toMoveX);
        toMoveX -= moveX;
        getNode().setLayoutX(getNode().getLayoutX() + moveX);
        return;
    }


    public Boolean intersectsWithEnemy(Enemies e){
        Bounds enemyBounds = GameState.getBoundswrtPane(e.getNode());
        Bounds weaponBounds = GameState.getBoundswrtPane(this.getNode());
        boolean ret = (!killedSomeone) && enemyBounds.intersects(weaponBounds);
        killedSomeone = ret || killedSomeone;
        return ret;
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }

}

class Shuriken extends Weapons{
    public static final String path = "AssetFXMLFiles/Shuriken.fxml";

    public Shuriken() {
        super(GameState.imageViewLoader(path));
        this.speedX = 15;
    }

}

class Knife extends Weapons{
    public static final String path = "AssetFXMLFiles/Knife.fxml";

    public Knife() {
        super(GameState.imageViewLoader(path));
        this.speedX = 10;
    }
}
