package com.javarush.island.zolotarev.island.config;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores.*;
import com.javarush.island.zolotarev.island.entity.organisms.animals.predators.*;
import com.javarush.island.zolotarev.island.entity.organisms.plants.Grass;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//Вероятности поедания (0–100)
public final class FoodMatrix {

    private static final Map<Class<?>, Map<Class<? extends Organism>, Integer>> MATRIX = new HashMap<>();

    static {
        registerPredators();
        registerHerbivores();
    }

    private FoodMatrix() {
    }

    private static void register(Class<?> eater, Class<? extends Organism> prey, int percent) {
        if (percent <= 0) {
            return;
        }
        MATRIX.computeIfAbsent(eater, k -> new HashMap<>()).put(prey, percent);
    }

    //Хищники
    private static void registerPredators() {
        register(Wolf.class, Horse.class, 10);
        register(Wolf.class, Deer.class, 15);
        register(Wolf.class, Rabbit.class, 60);
        register(Wolf.class, Mouse.class, 80);
        register(Wolf.class, Goat.class, 60);
        register(Wolf.class, Sheep.class, 70);
        register(Wolf.class, Boar.class, 15);
        register(Wolf.class, Buffalo.class, 10);
        register(Wolf.class, Duck.class, 40);

        register(Boa.class, Fox.class, 15);
        register(Boa.class, Rabbit.class, 20);
        register(Boa.class, Mouse.class, 40);
        register(Boa.class, Duck.class, 10);

        register(Fox.class, Rabbit.class, 70);
        register(Fox.class, Mouse.class, 90);
        register(Fox.class, Duck.class, 60);
        register(Fox.class, Caterpillar.class, 40);

        register(Bear.class, Boa.class, 80);
        register(Bear.class, Horse.class, 40);
        register(Bear.class, Deer.class, 80);
        register(Bear.class, Rabbit.class, 80);
        register(Bear.class, Mouse.class, 90);
        register(Bear.class, Goat.class, 70);
        register(Bear.class, Sheep.class, 70);
        register(Bear.class, Boar.class, 50);
        register(Bear.class, Buffalo.class, 20);
        register(Bear.class, Duck.class, 10);

        register(Eagle.class, Fox.class, 10);
        register(Eagle.class, Rabbit.class, 90);
        register(Eagle.class, Mouse.class, 90);
        register(Eagle.class, Duck.class, 80);
    }

    // Травоядные
    private static void registerHerbivores() {
        register(Horse.class, Grass.class, 100);
        register(Deer.class, Grass.class, 100);
        register(Rabbit.class, Grass.class, 100);
        register(Mouse.class, Caterpillar.class, 90);
        register(Mouse.class, Grass.class, 100);
        register(Goat.class, Grass.class, 100);
        register(Sheep.class, Grass.class, 100);
        register(Boar.class, Mouse.class, 50);
        register(Boar.class, Caterpillar.class, 90);
        register(Boar.class, Grass.class, 100);
        register(Buffalo.class, Grass.class, 100);
        register(Duck.class, Caterpillar.class, 90);
        register(Duck.class, Grass.class, 100);
        register(Caterpillar.class, Grass.class, 100);
    }

    // Шанс поедания. 0, если пары нет в таблице.
    public static int probability(Class<?> eater, Class<?> prey) {
        Map<Class<? extends Organism>, Integer> diet = MATRIX.get(eater);
        if (diet == null) {
            return 0;
        }
        return diet.getOrDefault(prey, 0);
    }


    // Копия рациона для вида (жертва → процент)
    public static Map<Class<? extends Organism>, Integer> dietFor(Class<?> eater) {
        Map<Class<? extends Organism>, Integer> diet = MATRIX.get(eater);
        if (diet == null) {
            return Map.of();
        }
        return Collections.unmodifiableMap(diet);
    }
}
