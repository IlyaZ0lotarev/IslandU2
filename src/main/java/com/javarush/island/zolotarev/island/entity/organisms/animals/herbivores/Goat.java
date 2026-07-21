package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Goat extends Herbivore {
    public Goat(int x, int y) {
        super(x, y, 60.0, 10.0, 3, 140);
    }

    @Override
    public String getIcon() {
        return "🐐";
    }
}
