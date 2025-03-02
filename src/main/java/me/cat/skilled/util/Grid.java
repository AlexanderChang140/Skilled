package me.cat.skilled.util;

public class Grid {
    private static final int DEFAULT_OFFSET = 8;
    private static final int TIER_OFFSET = 30;
    private static final int LINE_OFFSET = 60;

    public static int getPos(int index) {
        return index * DEFAULT_OFFSET;
    }

    public static int getPos(int index, int offset) {
        return offset * index;
    }

    public static int tierY(int tier) {
        int index = tier - 3;
        return -index * TIER_OFFSET - TIER_OFFSET / 2;
    }

    public static int lineX(int line) {
        int index = line - 2;
        return index * LINE_OFFSET;
    }
}
