package com.example.will_hero;

import javafx.scene.Node;

import java.util.ArrayList;

public class Hero extends GameObjects{
    public static final String path = "AssetFXMLFiles/Hero.fxml";
    private final int jumpX = 70;
    private final int jumpTIme = 200;
    private final Helmet helmet;

    public Hero(Node node) {
        super(node);
        helmet = new Helmet();
    }

    @Override
    public Boolean isColliding(Hero hero) {
        return null;
    }
}

class Helmet {
    private ArrayList<Weapons> WEAPONS_OPTIONS;
    public Helmet(){
        WEAPONS_OPTIONS = new ArrayList<>();
        WEAPONS_OPTIONS.add(new Weapon1());
        WEAPONS_OPTIONS.add(new Weapon2());
    }
    public Weapons getWeapon(){
        return null;
    }
}