package com.javarush.island.zolotarev.island.entity.organisms.animals.predators;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Predator;

public class Bear extends Predator {
    public Bear(int x, int y) {
        super(x, y, 500.0, 80.0, 2, 5);
    }

    @Override
    public String getIcon() {
        return "🐻";
    }
}
