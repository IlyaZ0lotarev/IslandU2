package com.javarush.island.zolotarev.island.entity.animals;

import com.javarush.island.zolotarev.island.entity.Organism;

public abstract class Animal extends Organism {
    protected int speed;

    public Animal(int x, int y, int weight, int maxFood, int foodEaten, int speed) {
        super(x, y, weight, maxFood, foodEaten);
        this.speed = speed;
    }
}
