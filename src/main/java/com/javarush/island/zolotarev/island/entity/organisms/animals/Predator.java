package com.javarush.island.zolotarev.island.entity.organisms.animals;

public abstract class Predator extends Animal {
    protected Predator(int x, int y, Class<? extends Predator> type) {
        super(x, y, type);
    }
}
