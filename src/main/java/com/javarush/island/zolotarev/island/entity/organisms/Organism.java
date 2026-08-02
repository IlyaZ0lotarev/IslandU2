package com.javarush.island.zolotarev.island.entity.organisms;

import com.javarush.island.zolotarev.island.config.OrganismRegistry;
import com.javarush.island.zolotarev.island.config.SpeciesSettings;
import com.javarush.island.zolotarev.island.entity.Entity;

public abstract class Organism extends Entity {
    protected double weight;
    protected final double maxWeight;
    protected double maxFood;
    protected double foodEaten;
    protected int maxPerCell;
    protected boolean alive = true;
    private final String icon;

    protected Organism(int x, int y, Class<? extends Organism> type) {
        super(x, y);
        SpeciesSettings settings = OrganismRegistry.require(type);
        this.weight = settings.weight;
        this.maxWeight = settings.weight;
        this.maxFood = settings.maxFood;
        this.maxPerCell = settings.maxPerCell;
        this.icon = settings.icon != null ? settings.icon : "?";
        this.foodEaten = 0.0;
    }

    @Override
    public String getIcon() {
        return icon;
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

    public abstract void metabolize();
}
