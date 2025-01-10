package me.cat.skilled.util;

import net.minecraft.util.Mth;

public class TickTimer {
    private int tickCounter = 0;
    private final int ticksPerAction;

    public TickTimer(int ticksPerAction) {
        this.ticksPerAction = ticksPerAction;
    }

    public boolean doTick() {
        tickCounter++;
        if (tickCounter >= ticksPerAction) {
            tickCounter = 0;
            return true;
        }
        return false;
    }

    public int getTickCounter() {
        return this.tickCounter;
    }

    public void setTickCounter(int ticks) {
        this.tickCounter = Mth.clamp(ticks, 0, ticksPerAction);
    }

    public int getTicksPerAction() {
        return this.ticksPerAction;
    }

    public void resetTickCounter() {
        this.tickCounter = 0;
    }
}
