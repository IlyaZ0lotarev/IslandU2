package com.javarush.island.zolotarev.island.config;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class SimulationConfig {

    private static final IslandSettings SETTINGS = SettingsLoader.get();

    public static final int ISLAND_WIDTH = SETTINGS.cols;
    public static final int ISLAND_HEIGHT = SETTINGS.rows;
    public static final long TICK_PERIOD_MS = SETTINGS.period;
    public static final long MAX_TICKS = SETTINGS.maxTicks;
    public static final boolean STOP_WHEN_NO_ANIMALS = SETTINGS.stopWhenNoAnimals;
    public static final double REPRODUCE_CHANCE = SETTINGS.reproduceChance;
    public static final int OFFSPRING_PER_BIRTH = SETTINGS.offspringPerBirth;
    public static final int PLANT_GROW_CHANCE_PERCENT = SETTINGS.percentPlantGrow;
    public static final int SHOW_ROWS = SETTINGS.showRows;
    public static final int SHOW_COLS = SETTINGS.showCols;
    public static final int CONSOLE_CELL_WIDTH = SETTINGS.consoleCellWith;
    public static final int PERCENT_ANIMAL_SLIM = SETTINGS.percentAnimalSlim;
    public static final int WORKER_POOL_SIZE = resolveWorkerPoolSize();

    private static final Map<Class<? extends Organism>, Integer> INITIAL_POPULATION = loadInitialPopulation();

    private SimulationConfig() {
    }

    public static IslandSettings settings() {
        return SETTINGS;
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

    private static int resolveWorkerPoolSize() {
        if (SETTINGS.workerPoolSize > 0) {
            return SETTINGS.workerPoolSize;
        }
        return Math.max(2, Runtime.getRuntime().availableProcessors());
    }

    private static Map<Class<? extends Organism>, Integer> loadInitialPopulation() {
        Map<Class<? extends Organism>, Integer> population = new LinkedHashMap<>();
        Map<String, Integer> fromYaml = SETTINGS.initialPopulation;
        if (fromYaml == null || fromYaml.isEmpty()) {
            return population;
        }
        for (Map.Entry<String, Integer> entry : fromYaml.entrySet()) {
            OrganismRegistry.find(entry.getKey()).ifPresent(type -> {
                int count = entry.getValue() != null ? entry.getValue() : 0;
                if (count > 0) {
                    population.put(type, count);
                }
            });
        }
        return population;
    }
}
