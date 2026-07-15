package com.javarush.island.zolotarev.island.entity.organisms.animals;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.entity.organisms.plants.Plant;

import java.util.*;

public abstract class Herbivore extends Animal {
    public Herbivore(int x, int y, double weight, double maxFood, int speed, int maxPerCell) {
        super(x, y, weight, maxFood, speed, maxPerCell);
    }

    @Override
    public Set<Class<? extends Organism>> getDiet() {
        return Set.of(Plant.class);
    }
}
