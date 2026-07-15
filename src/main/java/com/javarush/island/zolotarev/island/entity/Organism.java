package com.javarush.island.zolotarev.island.entity;

public abstract class Organism extends Entity {
    protected int weight;      // вес
    protected int maxFood;     // сколько еды нужно для насыщения
    protected int foodEaten;   // сколько уже съедено в этом такте
    protected boolean alive = true;

    public Organism(int x, int y, int weight, int maxFood, int foodEaten) {
        super(x, y);
        this.weight = weight;
        this.maxFood = maxFood;
        this.foodEaten = 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    public void addFoodEaten(int amount) {
        foodEaten += amount;
    }

    public void resetFoodEaten() {
        foodEaten = 0;
    }

    public boolean isHungry() {
        return foodEaten < maxFood;
    }
}
