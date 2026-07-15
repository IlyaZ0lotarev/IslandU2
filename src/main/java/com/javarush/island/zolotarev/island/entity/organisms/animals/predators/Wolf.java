package com.javarush.island.zolotarev.island.entity.organisms.animals.predators;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Predator;

public class Wolf extends Predator {
    public Wolf(int x, int y) {
        super(x, y, 50.0, 8.0, 3, 30);
    }

    @Override
    public String getIcon() {
        return "🐺";
    }
}
