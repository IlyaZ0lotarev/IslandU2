package com.javarush.island.zolotarev.island.entity.organisms.plants;

import com.javarush.island.zolotarev.island.config.SimulationConfig;
import com.javarush.island.zolotarev.island.entity.map.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Grass extends Plant {

    public Grass(int x, int y) {
        super(x, y, Grass.class);
    }

    @Override
    public void reproduce(Location location) {
        if (location.countOrganismsOfType(Grass.class) >= maxPerCell) {
            return;
        }
        if (ThreadLocalRandom.current().nextInt(100) < SimulationConfig.PLANT_GROW_CHANCE_PERCENT) {
            location.addOrganism(new Grass(x, y));
        }
    }
}
