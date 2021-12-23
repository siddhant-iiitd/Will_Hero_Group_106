package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Island extends GameObjects {
    public static String[] paths = {
            "AssetFXMLFiles/Island1.fxml",
            "AssetFXMLFiles/Island2.fxml",
            "AssetFXMLFiles/Island3.fxml",
            "AssetFXMLFiles/Island4.fxml",
            "AssetFXMLFiles/Island5.fxml"
    };
    private ImageView platformNode;

    protected Island(Node node) {
        super(node);
        Group platformGroup = (Group) node;
        platformNode = (ImageView) platformGroup.getChildren().get(0);
    }

    public Bounds islandBounds(){
        return platformNode.getParent().getBoundsInParent();
    }

    public ImageView getPlatformNode() {
        return this.platformNode;
    }

    //helper static function to create a new random island
    public static Island createIsland(){
        Random rand = new Random();
        String path = Island.paths[rand.nextInt(5)];
        return createIsland(path);
    }

    //helper static function to create a new island with given path
    public static Island createIsland(String path) {
        Group islandNode = GameState.groupLoader(path);
        Island island = new Island(islandNode);
        return island;
    }

    //checking if hero is colliding with island from above. We don't check from sides because
    // in that case we want it to just fall down into the abyss
    @Override
    public Boolean isColliding(Hero hero) {
        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        Bounds platformBounds = GameState.getBoundswrtPane(platformNode);
        if (heroBounds.intersects(platformBounds)) {
            if ((heroBounds.getMaxY() >= platformBounds.getMinY()) && (heroBounds.getMinY() <= platformBounds.getMinY() - hero.HEIGHT + 2)) {
                return true;
            }
        }
        return false;
    }
}
