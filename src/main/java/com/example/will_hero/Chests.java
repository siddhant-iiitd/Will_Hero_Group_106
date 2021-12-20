package com.example.will_hero;

public abstract class Chests {
    public static final String path = "AssetFXMLFiles/Chest.fxml";

    public abstract void open();
}

class CoinChest extends Chests {

    @Override
    public void open() {

    }
}
class WeaponsChest extends Chests {

    @Override
    public void open() {

    }
}