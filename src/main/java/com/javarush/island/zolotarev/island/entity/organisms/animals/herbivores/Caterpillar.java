package com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores;

import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.organisms.animals.Herbivore;
import com.javarush.island.zolotarev.island.util.Direction;

public class Caterpillar extends Herbivore {
    public Caterpillar(int x, int y) {
        super(x, y, 0.01, 0.01, 0, 1000);
    }

    @Override
    public String getIcon() {
        return "🐛";
    }

    @Override
    public Direction chooseMovementDirection() {
        return Direction.STAY;
    }

    @Override
    public void move(Island island) {
    }
}
