package com.javarush.island.zolotarev.island.entity.organisms;

import com.javarush.island.zolotarev.island.entity.Entity;

public abstract class Organism extends Entity {
    protected double weight;      // вес
    protected double maxFood;     // сколько еды нужно для насыщения
    protected double foodEaten;   // сколько уже съедено в этом такте
    protected int maxPerCell;      // макс. количество этого вида на одной клетке
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
        return foodEaten < maxFood;
    }
}
