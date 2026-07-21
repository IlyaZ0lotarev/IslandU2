package com.javarush.island.zolotarev.island.entity.organisms.animals;

import com.javarush.island.zolotarev.island.config.FoodMatrix;
import com.javarush.island.zolotarev.island.config.SimulationConfig;
import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.util.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Organism {
    protected int speed;

    public Animal(int x, int y, double weight, double maxFood, int speed, int maxPerCell) {
        super(x, y, weight, maxFood, maxPerCell);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void act(Island island) {
        if (!alive) {
            return;
        }
        Location location = island.getLocation(x, y);

        if (isHungry()) {
            eat(location);
        }
        if (alive && isHungry()) {
            move(island);
        }
        if (alive) {
            reproduce(location);
        }
    }

    /** Попытка поесть: для каждого типа добычи на клетке бросок по {@link FoodMatrix}. */
    public void eat(Location location) {
        if (!isHungry()) {
            return;
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (Map.Entry<Class<? extends Organism>, Integer> entry : FoodMatrix.dietFor(getClass()).entrySet()) {
            Class<? extends Organism> preyType = entry.getKey();
            List<Organism> preyOnCell = new ArrayList<>();
            for (Organism organism : location.getOrganismsOfType(preyType)) {
                if (organism.isAlive() && organism != this) {
                    preyOnCell.add(organism);
                }
            }
            if (preyOnCell.isEmpty()) {
                continue;
            }
            Organism prey = preyOnCell.get(random.nextInt(preyOnCell.size()));
            if (canEat(prey)) {
                addFoodEaten(prey.getWeight());
                prey.die();
                location.removeOrganism(prey);
            }
            if (!isHungry()) {
                return;
            }
        }
    }

    /**
     * Успех охоты: вероятность из таблицы (например, волк → кролик 60%).
     * {@link ThreadLocalRandom} — потокобезопасный генератор для многопоточной симуляции.
     */
    @Override
    public boolean canEat(Organism target) {
        if (target == null || !target.isAlive() || target == this) {
            return false;
        }
        int chance = FoodMatrix.probability(getClass(), target.getClass());
        if (chance <= 0) {
            return false;
        }
        return ThreadLocalRandom.current().nextInt(100) < chance;
    }

    @Override
    public Direction chooseMovementDirection() {
        return Direction.randomForMovement();
    }

    public void move(Island island) {
        if (speed <= 0) {
            return;
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int steps = random.nextInt(speed + 1);
        for (int i = 0; i < steps; i++) {
            Direction direction = chooseMovementDirection();
            int nx = x + direction.dx;
            int ny = y + direction.dy;
            if (!island.isValid(nx, ny)) {
                continue;
            }
            Location newLoc = island.getLocation(nx, ny);
            if (newLoc.countOrganismsOfType(getClass()) >= maxPerCell) {
                continue;
            }
            Location oldLoc = island.getLocation(x, y);
            oldLoc.removeOrganism(this);
            setCoordinates(nx, ny);
            newLoc.addOrganism(this);
        }
    }

    @Override
    public void reproduce(Location location) {
        if (!alive || location.countOrganismsOfType(getClass()) >= maxPerCell) {
            return;
        }
        boolean hasPartner = location.getOrganismsOfType(getClass()).stream()
                .anyMatch(o -> o != this && o.isAlive());
        if (!hasPartner) {
            return;
        }
        if (ThreadLocalRandom.current().nextDouble() >= SimulationConfig.REPRODUCE_CHANCE) {
            return;
        }
        try {
            Animal baby = getClass()
                    .getDeclaredConstructor(int.class, int.class)
                    .newInstance(x, y);
            location.addOrganism(baby);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    /** Смерть от голода, если за такт не набрано maxFood кг пищи. */
    @Override
    public void metabolize() {
        if (!alive || maxFood <= 0) {
            resetFoodEaten();
            return;
        }
        if (foodEaten < maxFood) {
            die();
        }
        resetFoodEaten();
    }
}
