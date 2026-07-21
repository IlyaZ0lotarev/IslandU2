package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Boar extends Herbivore {
    public Boar(int x, int y) {
        super(x, y, 400.0, 50.0, 2, 50);
    }

    @Override
    public String getIcon() {
        return "🐗";
    }
}
