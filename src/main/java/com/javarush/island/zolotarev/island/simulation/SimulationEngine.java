package com.javarush.island.zolotarev.island.simulation;

import com.javarush.island.zolotarev.island.api.view.View;
import com.javarush.island.zolotarev.island.config.SimulationConfig;
import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.entity.organisms.animals.Animal;
import com.javarush.island.zolotarev.island.entity.organisms.plants.Grass;
import com.javarush.island.zolotarev.island.view.console.ConsoleView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SimulationEngine {

    private static final long WORKER_AWAIT_MINUTES = 10;

    private final Island island;
    private final View view;
    private final ExecutorService workerPool;
    private long tick;

    public SimulationEngine(Island island) {
        this(island, ConsoleView.INSTANCE);
    }

    public SimulationEngine(Island island, View view) {
        this.island = island;
        this.view = view;
        this.tick = 0;
        this.workerPool = Executors.newFixedThreadPool(SimulationConfig.WORKER_POOL_SIZE);
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

    public void shutdown() {
        workerPool.shutdown();
        try {
            if (!workerPool.awaitTermination(WORKER_AWAIT_MINUTES, TimeUnit.MINUTES)) {
                workerPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            workerPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void runOneTick() {
        growPlants();
        List<Animal> animals = collectLiveAnimals();
        runAnimalPhase(animals);
        runMetabolismPhase(animals);
        removeDeadFromAllCells();
        tick++;
        view.show(tick, island);
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
        runInParallel(animals, animal -> animal.act(island));
    }

    private void runMetabolismPhase(List<Animal> animals) {
        runInParallel(animals, animal -> animal.metabolize());
    }

    private void runInParallel(List<Animal> animals, Consumer<Animal> action) {
        if (animals.isEmpty()) {
            return;
        }
        List<Callable<Void>> tasks = new ArrayList<>(animals.size());
        for (Animal animal : animals) {
            tasks.add(() -> {
                if (animal.isAlive()) {
                    action.accept(animal);
                }
                return null;
            });
        }
        awaitTasks(tasks);
    }

    private void awaitTasks(List<Callable<Void>> tasks) {
        try {
            workerPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Simulation worker pool interrupted", e);
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
