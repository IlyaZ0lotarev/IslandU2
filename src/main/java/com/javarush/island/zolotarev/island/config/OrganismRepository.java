package com.javarush.island.zolotarev.island.config;

import com.javarush.island.zolotarev.island.api.repository.Repository;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.Optional;

public final class OrganismRepository implements Repository {

    public static final OrganismRepository INSTANCE = new OrganismRepository();

    private OrganismRepository() {
    }

    @Override
    public Optional<Class<? extends Organism>> find(String name) {
        return OrganismRegistry.find(name);
    }
}
