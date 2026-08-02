package com.javarush.island.zolotarev.island.entity.organisms;

import com.javarush.island.zolotarev.island.entity.Entity;
import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.util.Direction;

public abstract class Organism extends Entity {
    protected double weight;
    protected double maxFood;
    protected double foodEaten;
    protected int maxPerCell;
    protected boolean alive = true;

    public Organism(int x, int y, double weight, double maxFood, int maxPerCell) {
        super(x, y);
        this.weight = weight;
        this.maxFood = maxFood;
        this.maxPerCell = maxPerCell;
        this.foodEaten = 0.0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
    }

    public double getWeight() {
        return weight;
    }

    public double getMaxFood() {
        return maxFood;
    }

    public double getFoodEaten() {
        return foodEaten;
    }

    public int getMaxPerCell() {
        return maxPerCell;
    }

    public void addFoodEaten(double amount) {
        foodEaten += amount;
    }

    public void resetFoodEaten() {
        foodEaten = 0.0;
    }

    public boolean isHungry() {
        if (maxFood <= 0) {
            return false;
        }
        return foodEaten < maxFood;
    }

    public abstract boolean canEat(Organism target);
    public abstract void reproduce(Location location);
    public abstract Direction chooseMovementDirection();
    public abstract void metabolize();

}
