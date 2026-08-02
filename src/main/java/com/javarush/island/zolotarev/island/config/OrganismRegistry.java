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
    private static final Map<Class<? extends Organism>, Organism> PROTOTYPE = new HashMap<>();

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
    }

    private OrganismRegistry() {
    }

    private static void register(Class<? extends Organism> type) {
        BY_NAME.put(type.getSimpleName(), type);
        PROTOTYPE.put(type, createPrototype(type));
    }

    public static Optional<Class<? extends Organism>> find(String name) {
        return Optional.ofNullable(BY_NAME.get(name));
    }

    public static String getIcon(Class<? extends Organism> type) {
        Organism prototype = PROTOTYPE.get(type);
        if (prototype == null) {
            return "?";
        }
        return prototype.getIcon();
    }

    public static int maxPerCell(Class<? extends Organism> type) {
        Organism prototype = PROTOTYPE.get(type);
        if (prototype == null) {
            return 1;
        }
        return prototype.getMaxPerCell();
    }

    private static Organism createPrototype(Class<? extends Organism> type) {
        try {
            return type.getConstructor(int.class, int.class).newInstance(0, 0);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot create prototype for " + type.getSimpleName(), e);
        }
    }
}
