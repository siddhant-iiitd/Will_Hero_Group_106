package com.example.will_hero;

import javafx.geometry.Bounds;
import javafx.scene.Node;

import java.io.Serializable;

public abstract class GameObjects implements Serializable {
    protected Node node;
    protected Location location;
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
    }

    public Location getLocation() {
        return this.location;
    }
    public void setLocation(Location loc) {
        this.location = loc;
    }
    public Node getNode() {
        return this.node;
    }
    public void setNode(Node n) {
        this.node = n;
    }

    public abstract Boolean isColliding(Hero hero);
}
