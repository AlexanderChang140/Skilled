package me.cat.skilled.util;

public class TickTimer {
    private int tickCounter = 0;
    private final int TICKS_PER_ACTION;

    public TickTimer(int TICKS_PER_ACTION) {
        this.TICKS_PER_ACTION = TICKS_PER_ACTION;
    }

    public boolean doTick() {
        tickCounter++;
        if (tickCounter >= TICKS_PER_ACTION) {
            tickCounter = 0;
            return true;
        }
        return false;
    }
}
