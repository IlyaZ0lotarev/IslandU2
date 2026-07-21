package com.javarush.island.zolotarev.island.entity.organisms.animals.predators;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Predator;

public class Fox extends Predator {
    public Fox(int x, int y) {
        super(x, y, 8.0, 2.0, 2, 30);
    }

    @Override
    public String getIcon() {
        return "🦊";
    }
}
