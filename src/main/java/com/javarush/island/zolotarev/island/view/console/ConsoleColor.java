package com.javarush.island.zolotarev.island.view.console;

public final class ConsoleColor {

    public static final String RESET = "\u001B[0m";
    public static final String FILL_RED = "\u001B[41m";
    public static final String FILL_GREEN = "\u001B[42m";
    public static final String FILL_YELLOW = "\u001B[43m";
    public static final String FILL_BLUE = "\u001B[44m";
    public static final String FILL_PURPLE = "\u001B[45m";
    public static final String FILL_WHITE = "\u001B[47m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String RED = "\u001B[31m";

    private static final String[] SCALE = {
            FILL_GREEN, FILL_BLUE, FILL_YELLOW, FILL_PURPLE, FILL_WHITE,
            GREEN, BLUE, YELLOW, PURPLE, RED,
    };

    private ConsoleColor() {
    }

    public static String forFill(int count, int maxCount) {
        if (maxCount <= 0) {
            return RESET;
        }
        if (count > maxCount) {
            return FILL_RED;
        }
        int index = SCALE.length - 1 - SCALE.length * count / (maxCount + 1);
        return SCALE[index];
    }
}
