package com.example.will_hero;

import javafx.scene.Node;

public class TNT extends GameObjects{
    public static final String path = "AssetFXMLFiles/TNT.fxml";

    protected TNT(Node node) {
        super(node);
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }
}
