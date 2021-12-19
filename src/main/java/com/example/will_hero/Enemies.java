package com.example.will_hero;

import javafx.scene.Node;

public abstract class Enemies extends GameObjects {
    protected Enemies(Node node) {
        super(node);
    }

    public String getPath(){
        return this.getPath();
    }
}

class RedOrc extends Enemies {
    public static final String path = "AssetFXMLFiles/RedOrc.fxml";

    public RedOrc(Node node) {
        super(node);
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }
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