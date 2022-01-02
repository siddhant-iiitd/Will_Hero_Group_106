package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Random;

public abstract class Chests extends GameObjects{
    static final long serialVersionUID = 12313L;

    public static final String path = "AssetFXMLFiles/Chest.fxml";
    public transient static ImageView openChest= GameState.imageViewLoader("AssetFXMLFiles/ChestOpen.fxml");
    private boolean isOpened = false;


    public abstract void open(GameState currState);

    public Chests(Node node) {
        super(node);
        node.setId("chest");
    }


    public boolean getIsOpened() {
        return isOpened;
    }

    public void setOpened(){
        this.isOpened = true;
    }

    @Override
    public Boolean isColliding(Hero hero) {
        // this function tells us if the collision bw hero and the chest is taking place (returns true)
        // or not (return false).

        // collision check function, island, enemy
        // opening,

//        System.out.println("Inside isColliding");
        if(isOpened){
            return false;
        }

        Bounds heroBounds = GameState.getBoundswrtPane(hero.getNode());
        Bounds chestBounds = GameState.getBoundswrtPane(node);
        if (heroBounds.intersects(chestBounds)) {
//            if ((heroBounds.getMaxY() >= chestBounds.getMinY()) && (heroBounds.getMinY() <= chestBounds.getMinY() - hero.HEIGHT + Math.abs(hero.getSpeedY()) + 0.5)) {
//                return true;
//            }

            return true;
        }
        return false;
    }

//    public static Chests addChests(){
//        ImageView chestnode = GameState.imageViewLoader(path);
//        CoinChest chest1= new CoinChest(chestnode);
//
//
//
//        System.out.println("Inside addChests which is in Chests-------------");
//
//        return chest1;
//    }

    public static Chests addChests(){
        ImageView chestnode = GameState.imageViewLoader(path);
        Random rand = new Random();
        Chests chest;
        if (rand.nextBoolean()) {
            chest = new CoinChest(chestnode);
        }
        else {
            chest = new WeaponsChest(chestnode);
        }
        return chest;
    }
}

class CoinChest extends Chests {
    static final long serialVersionUID = 801237L;

    public CoinChest(Node node) {
        super(node);
    }

    @Override
    public void open(GameState currState) {
        //GameState.coins = GameState.coins +10; //10 coins are gained on opening the CoinChest
        currState.addCoins(10);
        Sounds.coin();
    }

}
class WeaponsChest extends Chests {
    static final long serialVersionUID = 37210462L;

    public WeaponsChest(Node node) {
        super(node);
    }

    @Override
    public void open(GameState currState) {
        //returns either a shuriken or a knife
        Weapons w = currState.getHero().getHelmet().getWeapon();
        currState.getHero().setCurrWeapon(w);
    }
}