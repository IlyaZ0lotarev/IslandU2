package com.javarush.island.zolotarev.island.simulation;

import com.javarush.island.zolotarev.island.config.SimulationConfig;
import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public final class IslandInitializer {

    private static final int PLACEMENT_ATTEMPTS_PER_UNIT = 50;

    private IslandInitializer() {
    }

    public static Island createPopulatedIsland() {
        Island island = new Island(SimulationConfig.ISLAND_WIDTH, SimulationConfig.ISLAND_HEIGHT);
        for (Map.Entry<Class<? extends Organism>, Integer> entry :
                SimulationConfig.getInitialPopulation().entrySet()) {
            placeOrganisms(island, entry.getKey(), entry.getValue());
        }
        return island;
    }

    private static void placeOrganisms(Island island, Class<? extends Organism> type, int count) {
        if (count <= 0) {
            return;
        }
        Constructor<? extends Organism> constructor = resolveConstructor(type);
        int maxPerCell = createPrototype(constructor, 0, 0).getMaxPerCell();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int placed = 0;
        int maxAttempts = count * PLACEMENT_ATTEMPTS_PER_UNIT;

        for (int attempt = 0; placed < count && attempt < maxAttempts; attempt++) {
            int x = random.nextInt(island.getWidth());
            int y = random.nextInt(island.getHeight());
            Location location = island.getLocation(x, y);
            if (location.countOrganismsOfType(type) >= maxPerCell) {
                continue;
            }
            Organism organism = createPrototype(constructor, x, y);
            location.addOrganism(organism);
            placed++;
        }

        if (placed < count) {
            System.err.printf(
                    "Warning: placed only %d of %d %s (cells may be full)%n",
                    placed, count, type.getSimpleName()
            );
        }
    }

    private static Constructor<? extends Organism> resolveConstructor(Class<? extends Organism> type) {
        try {
            return type.getDeclaredConstructor(int.class, int.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(
                    "Organism " + type.getSimpleName() + " must have constructor (int x, int y)", e
            );
        }
    }

    private static Organism createPrototype(Constructor<? extends Organism> constructor, int x, int y) {
        try {
            return constructor.newInstance(x, y);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to instantiate organism", e);
        }
    }
}
