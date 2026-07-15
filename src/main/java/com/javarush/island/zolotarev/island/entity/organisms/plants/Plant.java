package com.javarush.island.zolotarev.island.entity.organisms.plants;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;

public class Plant extends Organism {
    public Plant(int x, int y, double weight, int maxPerCell) {
        super(x, y, weight, 0.0, maxPerCell);
    }

    public Plant(int x, int y) {
        this(x, y, 1.0, 200);
    }

    @Override
    public String getIcon() {
        return "♣";
    }
}
