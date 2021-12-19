package com.example.will_hero;

import javafx.scene.Node;

public class Hero extends GameObjects{
    private final int jumpX = 70;
    private final int jumpTIme = 200;

    public Hero(Node node) {
        super(node);
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }
}
