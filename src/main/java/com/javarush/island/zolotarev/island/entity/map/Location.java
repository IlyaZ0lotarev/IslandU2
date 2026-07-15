package com.javarush.island.zolotarev.island.entity.map;

import com.javarush.island.zolotarev.island.entity.Organism;

import java.util.*;

public class Location {
    private final Map<Class<? extends Organism>, Set<Organism>> organisms = new HashMap<>();

    public void addOrganism(Organism o) {
        organisms.computeIfAbsent(o.getClass(), k -> new HashSet<>()).add(o);
    }

    public void removeOrganism(Organism o) {
        Set<Organism> set = organisms.get(o.getClass());
        if (set != null) {
            set.remove(o);
            if (set.isEmpty()) {
                organisms.remove(o.getClass());
            }
        }
    }

    public Set<Organism> getOrganismsOfType(Class<? extends Organism> type) {
        return organisms.getOrDefault(type, Collections.emptySet());
    }

    public int countOrganismsOfType(Class<? extends Organism> type) {
        Set<Organism> set = organisms.get(type);
        return set != null ? set.size() : 0;
    }


    public Map<Class<? extends Organism>, Set<Organism>> getAllOrganisms() {
        return organisms;
    }

    public int countAllOrganisms() {
        return organisms.values().stream()
                .mapToInt(Set::size)
                .sum();
    }

    public boolean isEmpty() {
        return organisms.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Location{");
        organisms.forEach((type, set) -> {
            sb.append(type.getSimpleName()).append("=").append(set.size()).append(", ");
        });
        if (sb.length() > 9) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }
}
