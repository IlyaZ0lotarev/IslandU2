package com.javarush.island.zolotarev.island.config;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores.*;
import com.javarush.island.zolotarev.island.entity.organisms.animals.predators.*;
import com.javarush.island.zolotarev.island.entity.organisms.plants.Grass;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Параметры симуляции
 */
public final class SimulationConfig {

    public static final int ISLAND_WIDTH = 100;
    public static final int ISLAND_HEIGHT = 20;

    public static final long TICK_PERIOD_MS = 500;

    public static final long MAX_TICKS = 0;

    public static final boolean STOP_WHEN_NO_ANIMALS = true;

    public static final double REPRODUCE_CHANCE = 0.2;
    public static final int OFFSPRING_PER_BIRTH = 1;
    public static final int PLANT_GROW_CHANCE_PERCENT = 25;

    public static final int SHOW_ROWS = 5;
    public static final int SHOW_COLS = 40;
    public static final int CONSOLE_CELL_WIDTH = 2;

    public static final int WORKER_POOL_SIZE = Math.max(2, Runtime.getRuntime().availableProcessors());

    private static final Map<Class<? extends Organism>, Integer> INITIAL_POPULATION = new LinkedHashMap<>();

    static {
        registerInitialPopulation();
    }

    private SimulationConfig() {
    }

    private static void registerInitialPopulation() {
        INITIAL_POPULATION.put(Wolf.class, 30);
        INITIAL_POPULATION.put(Boa.class, 30);
        INITIAL_POPULATION.put(Fox.class, 30);
        INITIAL_POPULATION.put(Bear.class, 5);
        INITIAL_POPULATION.put(Eagle.class, 20);

        INITIAL_POPULATION.put(Horse.class, 20);
        INITIAL_POPULATION.put(Deer.class, 20);
        INITIAL_POPULATION.put(Rabbit.class, 150);
        INITIAL_POPULATION.put(Mouse.class, 500);
        INITIAL_POPULATION.put(Goat.class, 140);
        INITIAL_POPULATION.put(Sheep.class, 140);
        INITIAL_POPULATION.put(Boar.class, 50);
        INITIAL_POPULATION.put(Buffalo.class, 10);
        INITIAL_POPULATION.put(Duck.class, 200);
        INITIAL_POPULATION.put(Caterpillar.class, 1000);

        INITIAL_POPULATION.put(Grass.class, 5000);
    }

    public static Map<Class<? extends Organism>, Integer> getInitialPopulation() {
        return Collections.unmodifiableMap(INITIAL_POPULATION);
    }

    public static int getInitialCount(Class<? extends Organism> type) {
        return INITIAL_POPULATION.getOrDefault(type, 0);
    }

    public static boolean isTickLimitReached(long currentTick) {
        return MAX_TICKS > 0 && currentTick >= MAX_TICKS;
    }
}
