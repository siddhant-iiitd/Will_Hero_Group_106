package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Random;

public abstract class Chests extends GameObjects{
    public static final String path = "AssetFXMLFiles/Chest.fxml";
    public static ImageView openChest= GameState.imageViewLoader("AssetFXMLFiles/ChestOpen.fxml");
    private boolean isOpened = false;

    public abstract void open();

    public Chests(Node node) {
        super(node);
    }

    public boolean getIsOpened() {
        return isOpened;
    }

    public void setOpened(){
        this.isOpened = true;
    }

    @Override
    public Boolean isColliding(Hero hero) {
        // this function tells us if the collision bw hero and the chest is taking place (returns true)
        // or not (return false).

        // collision check function, island, enemy
        // opening,

        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        Bounds chestBounds = GameState.getBoundswrtPane(node);
        if (heroBounds.intersects(chestBounds)) {
//            if ((heroBounds.getMaxY() >= chestBounds.getMinY()) && (heroBounds.getMinY() <= chestBounds.getMinY() - hero.HEIGHT + Math.abs(hero.getSpeedY()) + 0.5)) {
//                return true;
//            }
            return true;
        }
        return false;
    }

    public static Chests addChests(){
        ImageView chestnode = GameState.imageViewLoader(path);
        Random rand = new Random();
        Chests chest;
        if (rand.nextBoolean()) {
            chest = new CoinChest(chestnode);
        }
        else {
            chest = new WeaponsChest(chestnode);
        }
        return chest;
    }
}

class CoinChest extends Chests {

    public CoinChest(Node node) {
        super(node);
    }

    @Override
    public void open() {

    }

}
class WeaponsChest extends Chests {
    private Weapons weapon;
    public WeaponsChest(Node node) {
        super(node);
    }

    @Override
    public void open() {

    }
}