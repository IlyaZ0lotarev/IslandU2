package com.javarush.island.zolotarev.island.entity.organisms.animals;

public abstract class Predator extends Animal {
    public Predator(int x, int y, double weight, double maxFood, int speed, int maxPerCell) {
        super(x, y, weight, maxFood, speed, maxPerCell);
    }
}
