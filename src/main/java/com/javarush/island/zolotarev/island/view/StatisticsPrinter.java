package com.javarush.island.zolotarev.island.view;

import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.Map;
import java.util.stream.Collectors;

public final class StatisticsPrinter {

    private StatisticsPrinter() {
    }

    public static void print(long tick, Island island) {
        Map<Class<? extends Organism>, Integer> stats = island.getStatistics();
        String line = stats.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((a, b) -> a.getSimpleName().compareTo(b.getSimpleName())))
                .map(e -> e.getKey().getSimpleName() + "=" + e.getValue())
                .collect(Collectors.joining(", "));
        System.out.printf("Tick %d | %s | total=%d%n", tick, line, island.getTotalOrganismCount());
    }
}
