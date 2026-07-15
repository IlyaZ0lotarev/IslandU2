package com.javarush.island.zolotarev.island.entity.animals.predators;

import com.javarush.island.zolotarev.island.entity.animals.Predator;

public class Wolf extends Predator {
    public Wolf(int x, int y) {
        super(x, y, 50.0, 8.0, 3, 30);
    }

    @Override
    public String getIcon() {
        return "🐺";
    }
}
