package me.cat.skilled.util;

public class Grid {
    private final int offset;

    public Grid(int offset) {
        this.offset = offset;
    }

    public int getPos(int index) {
        return offset * index;
    }
}
