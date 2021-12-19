package com.example.will_hero;

import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1;

    private Double x;
    private Double y;

    Location(double x, double y) {
        set(x, y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double x) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
