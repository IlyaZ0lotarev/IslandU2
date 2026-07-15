package com.javarush.island.zolotarev.island.entity;

public abstract class Entity {
    protected int x;
    protected int y;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinates (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract String getIcon();
}
