package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Buffalo extends Herbivore {
    public Buffalo(int x, int y) {
        super(x, y, 700.0, 100.0, 3, 10);
    }

    @Override
    public String getIcon() {
        return "🐃";
    }
}
