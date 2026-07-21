package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Horse extends Herbivore {
    public Horse(int x, int y) {
        super(x, y, 400.0, 60.0, 4, 20);
    }

    @Override
    public String getIcon() {
        return "🐎";
    }
}
