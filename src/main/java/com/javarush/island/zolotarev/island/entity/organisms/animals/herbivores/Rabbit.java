package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Rabbit extends Herbivore {
    public Rabbit(int x, int y) {
        super(x, y, 2.0, 0.45, 2, 150);
    }

    @Override
    public String getIcon() {
        return "🐇";
    }
}
