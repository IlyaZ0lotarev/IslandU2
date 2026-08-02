package com.javarush.island.zolotarev.island.entity.organisms.animals;

import com.javarush.island.zolotarev.island.api.entity.Eating;
import com.javarush.island.zolotarev.island.api.entity.Movable;
import com.javarush.island.zolotarev.island.api.entity.Reproducible;
import com.javarush.island.zolotarev.island.config.FoodMatrix;
import com.javarush.island.zolotarev.island.config.OrganismRegistry;
import com.javarush.island.zolotarev.island.config.SimulationConfig;
import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.util.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Organism implements Eating, Movable, Reproducible {
    protected int speed;

    protected Animal(int x, int y, Class<? extends Animal> type) {
        super(x, y, type);
        this.speed = OrganismRegistry.require(type).speed;
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
            spawn(location);
        }
    }

    @Override
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

    @Override
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
            Location.withLocks(oldLoc, newLoc, () -> {
                if (!oldLoc.getOrganismsOfType(getClass()).contains(this)) {
                    return;
                }
                if (newLoc.countOrganismsOfType(getClass()) >= maxPerCell) {
                    return;
                }
                oldLoc.removeOrganism(this);
                setCoordinates(nx, ny);
                newLoc.addOrganism(this);
            });
        }
    }

    @Override
    public void spawn(Location location) {
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
        int offspring = Math.max(1, SimulationConfig.OFFSPRING_PER_BIRTH);
        try {
            for (int i = 0; i < offspring; i++) {
                if (location.countOrganismsOfType(getClass()) >= maxPerCell) {
                    break;
                }
                Animal baby = getClass()
                        .getDeclaredConstructor(int.class, int.class)
                        .newInstance(x, y);
                location.addOrganism(baby);
            }
        } catch (ReflectiveOperationException ignored) {
        }
    }

    @Override
    public void metabolize() {
        if (!alive || maxFood <= 0) {
            resetFoodEaten();
            return;
        }
        if (foodEaten >= maxFood) {
            resetFoodEaten();
            return;
        }
        double hungerFraction = 1.0 - (foodEaten / maxFood);
        double loss = maxWeight * SimulationConfig.PERCENT_ANIMAL_SLIM / 100.0 * hungerFraction;
        weight = Math.max(0.0, weight - loss);
        if (weight <= 0.0) {
            die();
        }
        resetFoodEaten();
    }
}
