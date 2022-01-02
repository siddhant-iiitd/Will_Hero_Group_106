package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class TNT extends GameObjects{
    public static final String path = "AssetFXMLFiles/TNT.fxml";

    public transient static final ImageView explodingTNT= GameState.imageViewLoader("AssetFXMLFiles/Exploding.fxml");

    protected TNT(Node node) {
        super(node);
    }

    @Override
    public Boolean isColliding(Hero hero) {
        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        Bounds tntBounds = GameState.getBoundswrtPane(node);
        if (heroBounds.intersects(tntBounds)) {
//            if ((heroBounds.getMaxY() >= chestBounds.getMinY()) && (heroBounds.getMinY() <= chestBounds.getMinY() - hero.HEIGHT + Math.abs(hero.getSpeedY()) + 0.5)) {
//                return true;
//            }
            System.out.println("Hero collided w TNT");
            return true;
        }
        return false;

    }

    public static TNT addTnt(){
        ImageView tntNode = GameState.imageViewLoader(path);
        TNT tnt1= new TNT(tntNode);
        System.out.println("Inside addTnt");

        return tnt1;
    }
}
