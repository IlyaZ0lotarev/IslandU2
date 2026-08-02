package com.javarush.island.zolotarev.island.simulation;

import com.javarush.island.zolotarev.island.config.SimulationConfig;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public final class SimulationScheduler {

    private final SimulationEngine engine;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(false);
    private final CountDownLatch finished = new CountDownLatch(1);

    private volatile ScheduledExecutorService scheduler;

    public SimulationScheduler(SimulationEngine engine) {
        this.engine = engine;
    }

    public void start() {
        if (!started.compareAndSet(false, true)) {
            return;
        }
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "island-simulation");
            thread.setDaemon(false);
            return thread;
        });
        scheduler.scheduleWithFixedDelay(
                this::runTickSafely,
                0,
                SimulationConfig.TICK_PERIOD_MS,
                TimeUnit.MILLISECONDS
        );
    }

    private void runTickSafely() {
        try {
            if (engine.isFinished()) {
                requestStop();
                return;
            }
            engine.runOneTick();
            if (engine.isFinished()) {
                requestStop();
            }
        } catch (Exception e) {
            System.err.println("Simulation tick failed: " + e.getMessage());
            e.printStackTrace(System.err);
            requestStop();
        }
    }

    private void requestStop() {
        if (!stopped.compareAndSet(false, true)) {
            return;
        }
        ScheduledExecutorService local = scheduler;
        if (local != null) {
            local.shutdown();
        }
        finished.countDown();
    }

    public void awaitFinish() throws InterruptedException {
        finished.await();
        ScheduledExecutorService local = scheduler;
        if (local != null) {
            if (!local.awaitTermination(1, TimeUnit.MINUTES)) {
                local.shutdownNow();
            }
        }
    }
}
