package me.cat.skilled.skill;

import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

import java.awt.*;

public abstract class DurationSkill extends ActiveSkill {

    protected final boolean isCancelable;
    protected final TickTimer activeTimer;
    protected boolean isActive = false;

    public DurationSkill(int skillCooldown, int duration, boolean isCancelable) {
        super(skillCooldown);
        this.activeTimer = new TickTimer(duration);
        this.isCancelable = isCancelable;
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        if (!isActive) {
            onStart(serverPlayer);
        } else if (isCancelable) {
            onCancel(serverPlayer);
            return false;
        }
        return !isActive;
    }

    protected void onStart(ServerPlayer serverPlayer) {
        isActive = true;
        activeTimer.setTickCounter(0);
    }

    protected void onCancel(ServerPlayer serverPlayer) {
        isActive = false;
        activeTimer.setTickCounter(0);
        startCooldown();
    }

    public void onEnd(ServerPlayer serverPlayer) {
        isActive = false;
        activeTimer.setTickCounter(0);
        startCooldown();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putInt("activeTimer", activeTimer.getTickCounter());
        tag.putBoolean("is_active", isActive);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        activeTimer.setTickCounter(tag.getInt("activeTimer"));
        isActive = tag.getBoolean("is_active");
    }

    @Override
    public boolean doSkillTimer() {
        return !isActive;
    }

    @Override
    public int getCurrTick() {
        return isActive ? activeTimer.getTickCounter() : super.getCurrTick();
    }

    @Override
    public int getMaxTick() {
        return isActive ? activeTimer.getTicksPerAction() : super.getMaxTick();
    }

    @Override
    public boolean showCooldown() {
        return isActive || super.showCooldown();
    }

    @Override
    public Color getCooldownColorValues() {
        return isActive ? Color.YELLOW : super.getCooldownColorValues();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean tickActiveTimer() {
        return activeTimer.doTick();
    }
}
