package com.example.will_hero;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Chests extends GameObjects{
    public static final String path = "AssetFXMLFiles/Chest.fxml";
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
        return null;
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