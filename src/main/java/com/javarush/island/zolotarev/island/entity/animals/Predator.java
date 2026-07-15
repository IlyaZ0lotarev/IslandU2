package com.javarush.island.zolotarev.island.entity.animals;

import com.javarush.island.zolotarev.island.entity.Organism;

import java.util.*;

public abstract class Predator extends Animal {
    public Predator(int x, int y, double weight, double maxFood, int speed, int maxPerCell) {
        super(x, y, weight, maxFood, speed, maxPerCell);
    }

    @Override
    public Set<Class<? extends Organism>> getDiet() {
        // Все хищники едят всех травоядных
        Set<Class<? extends Organism>> diet = new HashSet<>();
        diet.add(Horse.class);
        diet.add(Deer.class);
        diet.add(Rabbit.class);
        diet.add(Mouse.class);
        diet.add(Goat.class);
        diet.add(Sheep.class);
        diet.add(Boar.class);
        diet.add(Buffalo.class);
        diet.add(Duck.class);
        diet.add(Caterpillar.class);
        return diet;
    }
}
