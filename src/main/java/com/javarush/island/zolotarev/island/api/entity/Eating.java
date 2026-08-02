package com.javarush.island.zolotarev.island.api.entity;

import com.javarush.island.zolotarev.island.entity.map.Location;

@FunctionalInterface
public interface Eating {

    void eat(Location location);
}
