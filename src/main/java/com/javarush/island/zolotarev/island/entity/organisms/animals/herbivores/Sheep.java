package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Sheep extends Herbivore {
    public Sheep(int x, int y) {
        super(x, y, 70.0, 15.0, 3, 140);
    }

    @Override
    public String getIcon() {
        return "🐑";
    }
}
