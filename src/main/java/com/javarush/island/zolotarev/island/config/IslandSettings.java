package com.javarush.island.zolotarev.island.config;

import java.util.LinkedHashMap;
import java.util.Map;

public class IslandSettings {
    public int period = 500;
    public int rows = 20;
    public int cols = 100;
    public long maxTicks = 0;
    public boolean stopWhenNoAnimals = true;
    public double reproduceChance = 0.2;
    public int offspringPerBirth = 1;
    public int workerPoolSize = 0;
    public int showRows = 5;
    public int showCols = 40;
    public int consoleCellWith = 2;
    public int percentAnimalSlim = 5;
    public int percentPlantGrow = 25;
    public Map<String, Integer> initialPopulation = new LinkedHashMap<>();
    public Map<String, Map<String, Integer>> foodMap = new LinkedHashMap<>();
}
