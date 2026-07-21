package com.javarush.island.zolotarev.island.entity.map;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.*;
import java.util.stream.Collectors;

public class Location {
    private final Map<Class<? extends Organism>, Set<Organism>> organisms = new HashMap<>();

    /** Две клетки в фиксированном порядке — чтобы не было взаимной блокировки при переходе. */
    public static void withLocks(Location first, Location second, Runnable action) {
        if (first == second) {
            synchronized (first) {
                action.run();
            }
            return;
        }
        Location lock1 = first;
        Location lock2 = second;
        if (System.identityHashCode(lock1) > System.identityHashCode(lock2)) {
            lock1 = second;
            lock2 = first;
        }
        synchronized (lock1) {
            synchronized (lock2) {
                action.run();
            }
        }
    }

    public synchronized void addOrganism(Organism o) {
        if (countOrganismsOfType(o.getClass()) >= o.getMaxPerCell()) {
            return;
        }
        organisms.computeIfAbsent(o.getClass(), k -> new HashSet<>()).add(o);
    }

    public synchronized void removeOrganism(Organism o) {
        Set<Organism> set = organisms.get(o.getClass());
        if (set != null) {
            set.remove(o);
            if (set.isEmpty()) {
                organisms.remove(o.getClass());
            }
        }
    }

    public synchronized void removeDeadOrganisms() {
        organisms.values().forEach(set -> set.removeIf(o -> !o.isAlive()));
        organisms.values().removeIf(Set::isEmpty);
    }

    public synchronized int countOrganismsOfType(Class<? extends Organism> type) {
        Set<Organism> set = organisms.get(type);
        return set != null ? set.size() : 0;
    }

    public synchronized Set<Organism> getOrganismsOfType(Class<? extends Organism> type) {
        Set<Organism> set = organisms.get(type);
        if (set == null || set.isEmpty()) {
            return Set.of();
        }
        return Set.copyOf(set);
    }

    public synchronized List<Organism> getAllOrganisms() {
        return organisms.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public synchronized int countAllOrganisms() {
        return organisms.values().stream()
                .mapToInt(Set::size)
                .sum();
    }

    public synchronized boolean isEmpty() {
        return organisms.isEmpty();
    }

    @Override
    public String toString() {
        synchronized (this) {
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
}
