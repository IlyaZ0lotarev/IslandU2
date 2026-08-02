package com.javarush.island.zolotarev.island.config;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;
import com.javarush.island.zolotarev.island.entity.organisms.animals.herbivores.*;
import com.javarush.island.zolotarev.island.entity.organisms.animals.predators.*;
import com.javarush.island.zolotarev.island.entity.organisms.plants.Grass;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class OrganismRegistry {

    private static final Map<String, Class<? extends Organism>> BY_NAME = new HashMap<>();
    private static final Map<Class<? extends Organism>, SpeciesSettings> SPECIES;

    static {
        register(Wolf.class);
        register(Boa.class);
        register(Fox.class);
        register(Bear.class);
        register(Eagle.class);
        register(Horse.class);
        register(Deer.class);
        register(Rabbit.class);
        register(Mouse.class);
        register(Goat.class);
        register(Sheep.class);
        register(Boar.class);
        register(Buffalo.class);
        register(Duck.class);
        register(Caterpillar.class);
        register(Grass.class);
        SPECIES = loadSpecies();
        validateAllSpeciesConfigured();
    }

    private OrganismRegistry() {
    }

    private static void register(Class<? extends Organism> type) {
        BY_NAME.put(type.getSimpleName(), type);
    }

    public static Optional<Class<? extends Organism>> find(String name) {
        return Optional.ofNullable(BY_NAME.get(name));
    }

    public static SpeciesSettings require(Class<? extends Organism> type) {
        SpeciesSettings settings = SPECIES.get(type);
        if (settings == null) {
            throw new IllegalStateException("Species settings not found for " + type.getSimpleName());
        }
        return settings;
    }

    public static String getIcon(Class<? extends Organism> type) {
        return require(type).icon;
    }

    public static int maxPerCell(Class<? extends Organism> type) {
        return require(type).maxPerCell;
    }

    private static Map<Class<? extends Organism>, SpeciesSettings> loadSpecies() {
        Map<Class<? extends Organism>, SpeciesSettings> species = new HashMap<>();
        Map<String, SpeciesSettings> fromYaml = SettingsLoader.get().species;
        if (fromYaml == null || fromYaml.isEmpty()) {
            return species;
        }
        for (Map.Entry<String, SpeciesSettings> entry : fromYaml.entrySet()) {
            find(entry.getKey()).ifPresent(type -> species.put(type, entry.getValue()));
        }
        return species;
    }

    private static void validateAllSpeciesConfigured() {
        for (Class<? extends Organism> type : BY_NAME.values()) {
            require(type);
        }
    }
}
