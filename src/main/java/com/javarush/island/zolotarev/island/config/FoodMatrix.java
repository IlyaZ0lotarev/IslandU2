package com.javarush.island.zolotarev.island.config;

import com.javarush.island.zolotarev.island.api.repository.Repository;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class FoodMatrix {

    private static final Repository REPOSITORY = OrganismRepository.INSTANCE;
    private static final Map<Class<?>, Map<Class<? extends Organism>, Integer>> MATRIX = loadFromSettings();

    private FoodMatrix() {
    }

    private static Map<Class<?>, Map<Class<? extends Organism>, Integer>> loadFromSettings() {
        Map<Class<?>, Map<Class<? extends Organism>, Integer>> matrix = new HashMap<>();
        Map<String, Map<String, Integer>> foodMap = SettingsLoader.get().foodMap;
        if (foodMap == null) {
            return matrix;
        }
        for (Map.Entry<String, Map<String, Integer>> eaterEntry : foodMap.entrySet()) {
            REPOSITORY.find(eaterEntry.getKey()).ifPresent(eaterClass -> {
                Map<String, Integer> preyMap = eaterEntry.getValue();
                if (preyMap == null || preyMap.isEmpty()) {
                    return;
                }
                for (Map.Entry<String, Integer> preyEntry : preyMap.entrySet()) {
                    REPOSITORY.find(preyEntry.getKey()).ifPresent(preyClass -> {
                        int percent = preyEntry.getValue() != null ? preyEntry.getValue() : 0;
                        if (percent > 0) {
                            matrix.computeIfAbsent(eaterClass, k -> new HashMap<>())
                                    .put(preyClass, percent);
                        }
                    });
                }
            });
        }
        return matrix;
    }

    public static int probability(Class<?> eater, Class<?> prey) {
        Map<Class<? extends Organism>, Integer> diet = MATRIX.get(eater);
        if (diet == null) {
            return 0;
        }
        return diet.getOrDefault(prey, 0);
    }

    public static Map<Class<? extends Organism>, Integer> dietFor(Class<?> eater) {
        Map<Class<? extends Organism>, Integer> diet = MATRIX.get(eater);
        if (diet == null) {
            return Map.of();
        }
        return Collections.unmodifiableMap(diet);
    }
}
