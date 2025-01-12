package me.cat.skilled.skill.instance;

import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public abstract class ActiveSkill extends Skill {
    private final TickTimer skillCooldown;
    private boolean isSkillReady = true;

    protected ActiveSkill(int ticksPerAction) {
        super();
        skillCooldown = new TickTimer(ticksPerAction);
    }

    protected abstract boolean onActivateSkill(ServerPlayer serverPlayer);

    public void activateSkill(ServerPlayer serverPlayer) {
        if (!isSkillReady) {
            return;
        }
        isSkillReady = !onActivateSkill(serverPlayer);
    }

    public void checkSkillReady() {
        if (!isSkillReady && skillCooldown.doTick()) {
            isSkillReady = true;
        }
    }

    public int getCurrTick() {
        return skillCooldown.getTickCounter();
    }

    public int getMaxTick() {
        return skillCooldown.getTicksPerAction();
    }

    @Override
    public CompoundTag saveNbt() {
        CompoundTag tag = super.saveNbt();
        tag.putInt("skill_cooldown", skillCooldown.getTickCounter());
        tag.putBoolean("is_skill_ready", isSkillReady);
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        super.loadNbt(tag);
        skillCooldown.setTickCounter(tag.getInt("skill_cooldown"));
        isSkillReady = tag.getBoolean("is_skill_ready");
    }
}
