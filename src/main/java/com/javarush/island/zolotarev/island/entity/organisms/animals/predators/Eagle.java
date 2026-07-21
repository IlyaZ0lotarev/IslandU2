package com.javarush.island.zolotarev.island.entity.organisms.animals.predators;

import com.javarush.island.zolotarev.island.entity.organisms.animals.Predator;

public class Eagle extends Predator {
    public Eagle(int x, int y) {
        super(x, y, 6.0, 1.0, 3, 20);
    }

    @Override
    public String getIcon() {
        return "🦅";
    }
}
