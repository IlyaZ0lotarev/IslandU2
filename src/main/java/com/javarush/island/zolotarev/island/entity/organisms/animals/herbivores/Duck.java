package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;

public class Duck extends Herbivore {
    public Duck(int x, int y) {
        super(x, y, 1.0, 0.15, 4, 200);
    }

    @Override
    public String getIcon() {
        return "🦆";
    }
}
