package com.javarush.island.zolotarev.island.api.repository;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.Optional;

public interface Repository {

    Optional<Class<? extends Organism>> find(String name);
}
