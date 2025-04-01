package me.cat.skilled.skill;

import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

import java.awt.*;

public abstract class ActiveSkill extends Skill {
    private final TickTimer skillTimer;
    protected boolean isSkillReady = true;

    protected ActiveSkill(int skillCooldown) {
        this.skillTimer = new TickTimer(skillCooldown);
    }

    protected abstract boolean onActivateSkill(ServerPlayer serverPlayer);

    public void activateSkill(ServerPlayer serverPlayer) {
        if (isSkillReady) {
            isSkillReady = !onActivateSkill(serverPlayer);
        }
    }

    public void tickSkillTimer() {
        if (doSkillTimer() && !isSkillReady && skillTimer.doTick()) {
            isSkillReady = true;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putInt("skill_cooldown", skillTimer.getTickCounter());
        tag.putBoolean("is_skill_ready", isSkillReady);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        skillTimer.setTickCounter(tag.getInt("skill_cooldown"));
        isSkillReady = tag.getBoolean("is_skill_ready");
    }

    protected boolean doSkillTimer() {
        return true;
    }

    public int getCurrTick() {
        return skillTimer.getTickCounter();
    }

    public int getMaxTick() {
        return skillTimer.getTicksPerAction();
    }

    public boolean showCooldown() {
        return !isSkillReady;
    }

    public Color getCooldownColorValues() {
        return Color.BLACK;
    }

    public String getHudValue() {
        return "";
    }
}
