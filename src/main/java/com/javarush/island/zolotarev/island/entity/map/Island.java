package com.javarush.island.zolotarev.island.entity.map;

import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Organism> getAllOrganisms() {
        List<Organism> all = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                all.addAll(locations[x][y].getAllOrganisms());
            }
        }
        return all;
    }

    public Map<Class<? extends Organism>, Integer> getStatistics() {
        Map<Class<? extends Organism>, Integer> stats = new HashMap<>();
        for (Organism organism : getAllOrganisms()) {
            if (organism.isAlive()) {
                stats.merge(organism.getClass(), 1, Integer::sum);
            }
        }
        return stats;
    }

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
