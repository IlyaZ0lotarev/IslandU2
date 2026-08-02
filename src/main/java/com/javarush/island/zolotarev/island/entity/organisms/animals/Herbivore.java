package com.javarush.island.zolotarev.island.entity.organisms.animals;

public abstract class Herbivore extends Animal {
    protected Herbivore(int x, int y, Class<? extends Herbivore> type) {
        super(x, y, type);
    }
}
