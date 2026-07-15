package com.javarush.island.zolotarev.island.entity.organisms.animals;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.map.Location;

import java.util.*;


public abstract class Animal extends Organism {
    protected int speed;

    public Animal(int x, int y, double weight, double maxFood, int speed, int maxPerCell) {
        super(x, y, weight, maxFood, maxPerCell);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    //Рацион — классы организмов, которые данный вид может есть
    public abstract Set<Class<? extends Organism>> getDiet();

    //Метод поведения за один такт
    public void act(Island island) {
        if (!alive) return;
        Location location = island.getLocation(x, y);

        if (isHungry()) {
            eat(location);
        }
        if (alive && isHungry()) {
            move(island);
        }
        if (alive) {
            reproduce(location, island);
        }
    }

    protected void eat(Location location) {
        for (Class<? extends Organism> foodClass : getDiet()) {
            Set<Organism> foods = location.getOrganismsOfType(foodClass);
            if (!foods.isEmpty()) {
                Organism prey = foods.iterator().next();
                addFoodEaten(prey.getWeight());
                prey.die();
                location.removeOrganism(prey);
                break;
            }
        }
    }

    protected void move(Island island) {
        int steps = (int) (Math.random() * (speed + 1));
        for (int i = 0; i < steps; i++) {
            int dx = (int) (Math.random() * 3) - 1; // -1, 0, 1
            int dy = (int) (Math.random() * 3) - 1;
            int nx = x + dx, ny = y + dy;
            if (island.isValid(nx, ny) && !(dx == 0 && dy == 0)) {
                Location oldLoc = island.getLocation(x, y);
                Location newLoc = island.getLocation(nx, ny);
                oldLoc.removeOrganism(this);
                setCoordinates(nx, ny);
                newLoc.addOrganism(this);
                break;
            }
        }
    }

    protected void reproduce(Location location, Island island) {
        Set<Organism> sameSpecies = location.getOrganismsOfType(getClass());
        boolean hasPartner = sameSpecies.stream()
                .anyMatch(o -> o != this && o.isAlive());
        if (hasPartner && Math.random() < 0.2) {
            try {
                Animal baby = getClass()
                        .getDeclaredConstructor(int.class, int.class)
                        .newInstance(x, y);
                location.addOrganism(baby);
            } catch (Exception ignored) {

            }
        }
    }
}
