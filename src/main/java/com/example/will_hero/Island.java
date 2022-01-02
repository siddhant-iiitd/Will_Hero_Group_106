package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Island extends GameObjects {
    static final long serialVersionUID = 532L;
    public static String[] paths = {
            "AssetFXMLFiles/Island1.fxml",
            "AssetFXMLFiles/Island2.fxml",
            "AssetFXMLFiles/Island3.fxml",
            "AssetFXMLFiles/Island4.fxml",
            "AssetFXMLFiles/Island5.fxml"
    };
    private transient Node platformNode;

    public Island(Node node) {
        super(node);
        Group platformGroup = (Group) node;
        platformNode = (ImageView) platformGroup.getChildren().get(0);
    }

    public Bounds islandBounds(){
        return platformNode.getParent().getBoundsInParent();
    }

    public Node getPlatformNode() {
        return this.platformNode;
    }
    public void setPlatformNode(Node node){
        platformNode = node;
    }


    //helper static function to create a new random island
    public static Island createIsland(){
        Random rand = new Random();
        int i = rand.nextInt(5);
        String path = Island.paths[i];
        Island is = createIsland(path);
        is.node.setId("island" + i);
        return is;
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
            if ((heroBounds.getMaxY() >= platformBounds.getMinY()) && (heroBounds.getMinY() <= platformBounds.getMinY() - hero.HEIGHT + Math.abs(hero.getSpeedY()) + 0.5)) {
                return true;
            }
        }
        return false;
    }

    public Boolean collidingEnemy(Enemies enemy) {
        Bounds enemyBounds = GameState.getBoundswrtPane(enemy.getNode());
        Bounds platformBounds = GameState.getBoundswrtPane(platformNode);
        if (enemyBounds.intersects(platformBounds)) {
            if ((enemyBounds.getMaxY() >= platformBounds.getMinY()) && (enemyBounds.getMinY() <= platformBounds.getMinY() - enemy.HEIGHT + Math.abs(enemy.getSpeedY()) + 0.5)) {
                return true;
            }
        }
        return false;
    }
}
