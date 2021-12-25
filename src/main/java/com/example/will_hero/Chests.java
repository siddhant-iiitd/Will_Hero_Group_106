package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

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
        ImageView chestnode = GameState.imageViewLoader(path);
        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        Bounds chestBounds = GameState.getBoundswrtPane(chestnode);
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
        CoinChest chest1= new CoinChest(chestnode);
        return chest1;
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

    public WeaponsChest(Node node) {
        super(node);
    }

    @Override
    public void open() {

    }
}