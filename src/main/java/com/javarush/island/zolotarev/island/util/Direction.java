package com.javarush.island.zolotarev.island.util;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    STAY(0, 0);

    public final int dx;
    public final int dy;

    private static final Direction[] VALUES = values();
    private static final int VALUES_LENGTH = VALUES.length;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction random() {
        return VALUES[ThreadLocalRandom.current().nextInt(VALUES_LENGTH)];
    }

    public static Direction randomForMovement() {
        Direction direction;
        do {
            direction = random();
        } while (direction == STAY);
        return direction;
    }
}
