package com.javarush.island.zolotarev.island.entity.map;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.*;

public class Island {
    private final int width;
    private final int height;
    private final Location[][] locations;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.locations = new Location[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = new Location();
            }
        }
    }

    public Location getLocation(int x, int y) {
        return locations[x][y];
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // список всех организмов на острове.
    public List<Organism> getAllOrganisms() {
        List<Organism> all = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location loc = locations[x][y];
                for (Set<Organism> set : loc.getAllOrganisms().values()) {
                    all.addAll(set);
                }
            }
        }
        return all;
    }

    // Статистика по количеству организмов каждого типа на острове.
    public Map<Class<? extends Organism>, Integer> getStatistics() {
        Map<Class<? extends Organism>, Integer> stats = new HashMap<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location loc = locations[x][y];
                for (Map.Entry<Class<? extends Organism>, Set<Organism>> entry :
                        loc.getAllOrganisms().entrySet()) {
                    Class<? extends Organism> type = entry.getKey();
                    int count = entry.getValue().size();
                    stats.merge(type, count, Integer::sum);
                }
            }
        }
        return stats;
    }

    // общее количество всех организмов на острове.
    public int getTotalOrganismCount() {
        int total = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                total += locations[x][y].countAllOrganisms();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return String.format("Island{width=%d, height=%d, totalOrganisms=%d}",
                width, height, getTotalOrganismCount());
    }
}
