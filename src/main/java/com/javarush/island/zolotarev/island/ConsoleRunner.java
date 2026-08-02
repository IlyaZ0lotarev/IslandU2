package com.javarush.island.zolotarev.island;

import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.simulation.IslandInitializer;
import com.javarush.island.zolotarev.island.simulation.SimulationEngine;
import com.javarush.island.zolotarev.island.simulation.SimulationScheduler;

public class ConsoleRunner {
    public static void main(String[] args) throws InterruptedException {
        Island island = IslandInitializer.createPopulatedIsland();
        SimulationEngine engine = new SimulationEngine(island);
        SimulationScheduler scheduler = new SimulationScheduler(engine);

        System.out.println("Simulation started: " + island);
        scheduler.start();
        scheduler.awaitFinish();
        System.out.println("Simulation finished at tick " + engine.getTick());
    }
}
