package com.javarush.island.zolotarev.island.entity.organisms.plants;

import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.util.Direction;

public abstract class Plant extends Organism {

    public Plant(int x, int y, double weight, int maxPerCell) {
        super(x, y, weight, 0.0, maxPerCell);
    }

    public Plant(int x, int y) {
        this(x, y, 1.0, 200);
    }

    @Override
    public boolean canEat(Organism target) {
        return false;
    }

    @Override
    public Direction chooseMovementDirection() {
        return Direction.STAY;
    }

    @Override
    public void metabolize() {
        resetFoodEaten();
    }

    @Override
    public abstract void reproduce(Location location);

    @Override
    public String getIcon() {
        return "♣";
    }
}
