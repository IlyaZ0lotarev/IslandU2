package com.javarush.island.zolotarev.island.simulation;

import com.javarush.island.zolotarev.island.config.SimulationConfig;
import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.entity.organisms.animals.Animal;
import com.javarush.island.zolotarev.island.entity.organisms.plants.Grass;
import com.javarush.island.zolotarev.island.view.StatisticsPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private static final long WORKER_AWAIT_MINUTES = 10;

    private final Island island;
    private long tick;

    public SimulationEngine(Island island) {
        this.island = island;
        this.tick = 0;
    }

    public Island getIsland() {
        return island;
    }

    public long getTick() {
        return tick;
    }

    public boolean isFinished() {
        if (SimulationConfig.isTickLimitReached(tick)) {
            return true;
        }
        return SimulationConfig.STOP_WHEN_NO_ANIMALS && countLiveAnimals() == 0;
    }


    public void runOneTick() {
        growPlants();
        List<Animal> animals = collectLiveAnimals();
        runAnimalPhase(animals);
        runMetabolismPhase(animals);
        removeDeadFromAllCells();
        tick++;
        StatisticsPrinter.print(tick, island);
    }

    private void growPlants() {
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location location = island.getLocation(x, y);
                List<Grass> grasses = new ArrayList<>();
                for (Organism organism : location.getAllOrganisms()) {
                    if (organism instanceof Grass grass && grass.isAlive()) {
                        grasses.add(grass);
                    }
                }
                for (Grass grass : grasses) {
                    grass.reproduce(location);
                }
            }
        }
    }

    private void runAnimalPhase(List<Animal> animals) {
        if (animals.isEmpty()) {
            return;
        }
        ExecutorService pool = Executors.newFixedThreadPool(SimulationConfig.WORKER_POOL_SIZE);
        for (Animal animal : animals) {
            pool.submit(() -> {
                if (animal.isAlive()) {
                    animal.act(island);
                }
            });
        }
        shutdownAndAwait(pool);
    }

    private void runMetabolismPhase(List<Animal> animals) {
        if (animals.isEmpty()) {
            return;
        }
        ExecutorService pool = Executors.newFixedThreadPool(SimulationConfig.WORKER_POOL_SIZE);
        for (Animal animal : animals) {
            pool.submit(() -> {
                if (animal.isAlive()) {
                    animal.metabolize();
                }
            });
        }
        shutdownAndAwait(pool);
    }

    private void shutdownAndAwait(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(WORKER_AWAIT_MINUTES, TimeUnit.MINUTES)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private List<Animal> collectLiveAnimals() {
        List<Animal> animals = new ArrayList<>();
        for (Organism organism : island.getAllOrganisms()) {
            if (organism instanceof Animal animal && animal.isAlive()) {
                animals.add(animal);
            }
        }
        return animals;
    }

    private void removeDeadFromAllCells() {
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                island.getLocation(x, y).removeDeadOrganisms();
            }
        }
    }

    private long countLiveAnimals() {
        return collectLiveAnimals().size();
    }
}
