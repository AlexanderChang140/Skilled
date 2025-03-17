package me.cat.skilled.skill;

import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

import java.awt.*;

public class ToggleableSkill extends ActiveSkill {
    private final TickTimer toggleTimer = new TickTimer(20);
    private boolean canToggle = true;
    private boolean isToggled = false;
    protected Color toggledColor = Color.YELLOW;

    public ToggleableSkill(int ticksPerAction) {
        super(ticksPerAction);
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        if (canToggle) {
            isToggled = !isToggled;
            canToggle = false;
        }
        return false;
    }

    protected void consume() {
        isSkillReady = false;
        isToggled = false;
        syncSkill();
    }

    public void tickToggleTimer() {
        if (!canToggle && toggleTimer.doTick()) {
            canToggle = true;
        }
    }

    @Override
    public CompoundTag saveNbt() {
        CompoundTag tag = super.saveNbt();
        tag.putInt("toggle_timer", toggleTimer.getTickCounter());
        tag.putBoolean("can_toggle", canToggle);
        tag.putBoolean("is_toggled", isToggled);
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        super.loadNbt(tag);
        toggleTimer.setTickCounter(tag.getInt("toggle_timer"));
        canToggle = tag.getBoolean("can_toggle");
        isToggled = tag.getBoolean("is_toggled");
    }

    protected boolean isToggled() {
        return isToggled;
    }

    @Override
    public int getCurrTick() {
        return isToggled ? -1 : super.getCurrTick();
    }

    @Override
    public boolean showCooldown() {
        return isToggled || super.showCooldown();
    }

    @Override
    public Color getCooldownColorValues() {
        return isToggled ? toggledColor : super.getCooldownColorValues();
    }
}
