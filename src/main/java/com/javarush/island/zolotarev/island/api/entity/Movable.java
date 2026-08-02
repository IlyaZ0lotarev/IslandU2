package com.javarush.island.zolotarev.island.api.entity;

import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.util.Direction;

public interface Movable {

    void move(Island island);

    Direction chooseMovementDirection();
}
