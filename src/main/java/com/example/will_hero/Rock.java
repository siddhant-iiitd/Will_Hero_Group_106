package com.example.will_hero;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Rock extends GameObjects {
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
