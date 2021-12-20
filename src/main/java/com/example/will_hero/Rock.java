package com.example.will_hero;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Rock extends GameObjects {
    public static String[] paths = {
            "AssetFXMLFiles/Rock1.fxml",
            "AssetFXMLFiles/Rock2.fxml",
            "AssetFXMLFiles/Rock3.fxml",
            "AssetFXMLFiles/Rock4.fxml",
            "AssetFXMLFiles/Rock5.fxml"
    };
    private ImageView platformNode;

    protected Rock(Node node) {
        super(node);
        Group platformGroup = (Group) node;
        platformNode = (ImageView) platformGroup.getChildren().get(0);
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }
}
