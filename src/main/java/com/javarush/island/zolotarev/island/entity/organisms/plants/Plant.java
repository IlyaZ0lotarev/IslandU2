package com.javarush.island.zolotarev.island.entity.organisms.plants;

import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;

public abstract class Plant extends Organism {

    protected Plant(int x, int y, Class<? extends Plant> type) {
        super(x, y, type);
    }

    @Override
    public boolean canEat(Organism target) {
        return false;
    }

    @Override
    public void metabolize() {
        resetFoodEaten();
    }

    public abstract void reproduce(Location location);
}
