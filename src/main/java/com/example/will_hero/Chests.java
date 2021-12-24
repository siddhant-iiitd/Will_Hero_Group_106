package com.example.will_hero;

import javafx.scene.Node;

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