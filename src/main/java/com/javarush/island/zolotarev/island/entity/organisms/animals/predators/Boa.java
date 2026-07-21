package com.javarush.island.zolotarev.island.entity.organisms.animals.predators;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Predator;

public class Boa extends Predator {
    public Boa(int x, int y) {
        super(x, y, 15.0, 3.0, 1, 30);
    }

    @Override
    public String getIcon() {
        return "🐍";
    }
}
