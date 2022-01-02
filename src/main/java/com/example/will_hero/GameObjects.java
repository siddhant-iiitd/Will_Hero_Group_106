package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;

import java.io.Serializable;

public abstract class GameObjects implements Serializable {
    static final long serialVersionUID = 7493L;
    protected transient Node node;
    protected NodeInformation info;
    protected final double WIDTH;
    protected final double HEIGHT;
    protected final double SCENE_WIDTH = 720;
    protected final double SCENE_HEIGHT = 576;

    protected GameObjects(Node node) {
        this.node = node;
        Bounds bounds = this.node.getBoundsInLocal();
        Bounds absoluteBounds = node.localToScene(node.getBoundsInLocal());
        this.WIDTH = bounds.getWidth();
        this.HEIGHT = bounds.getHeight();
        info = new NodeInformation();
    }

    public void updateInfo(){
        info.setNodeInfo(node);
    }

    public Node restoreNode(){
        System.out.println(info.id);
        System.out.println(info.path);
        node = GameState.nodeLoader(info.path);
        node.setLayoutY(info.layoutY);
        node.setLayoutX(info.layoutX);
        node.setId(info.id);
        return node;
    }

    public Node getNode() {
        return this.node;
    }
    public void setNode(Node n) {
        this.node = n;
    }

    public abstract Boolean isColliding(Hero hero);
}

class NodeInformation implements Serializable{
    protected double layoutX;
    protected double layoutY;
    protected String path;
    protected String id;

    public NodeInformation(){
    }

    public void setNodeInfo(Node node){
        layoutY = node.getLayoutY();
        layoutX = node.getLayoutX();
        id = node.getId();
        if (id.equals("chest")) path = Chests.path;
        else if (id.equals("redorc")) path = RedOrc.path;
        else if (id.equals("greenorc")) path = GreenOrc.path;
        else if (id.equals("boss")) path = Boss.path;
        else if (id.equals("hero")) path = Hero.path;
        else if (id.equals("island0")) path = Island.paths[0];
        else if (id.equals("island1")) path = Island.paths[1];
        else if (id.equals("island2")) path = Island.paths[2];
        else if (id.equals("island3")) path = Island.paths[3];
        else if (id.equals("island4")) path = Island.paths[4];
        else if (id.equals("tnt")) path = TNT.path;
        else if (id.equals("knife")) path = Knife.path;
        else if (id.equals("shuriken")) path = Shuriken.path;
        else if (id.equals("bossisland")) path = "AssetFXMLFiles/BossIsland.fxml";
    }
}