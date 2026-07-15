package com.javarush.island.zolotarev.island.entity;

public class Plant extends Organism {

    public Plant(int x, int y) {
        super(x, y, 1, 0, 0);
    }

    @Override
    public String getIcon() {
        return "♣";
    }
}
