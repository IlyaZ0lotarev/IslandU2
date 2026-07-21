package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Deer extends Herbivore {
    public Deer(int x, int y) {
        super(x, y, 300.0, 50.0, 4, 20);
    }

    @Override
    public String getIcon() {
        return "🦌";
    }
}
