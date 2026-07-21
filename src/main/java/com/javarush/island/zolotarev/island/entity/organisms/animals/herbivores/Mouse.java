package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Mouse extends Herbivore {
    public Mouse(int x, int y) {
        super(x, y, 0.05, 0.01, 1, 500);
    }

    @Override
    public String getIcon() {
        return "🐁";
    }
}
