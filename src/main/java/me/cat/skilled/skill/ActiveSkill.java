package me.cat.skilled.skill;

import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public abstract class ActiveSkill extends Skill {
    private final TickTimer skillCooldown;
    private boolean isSkillReady = true;

    protected ActiveSkill(int maxLevel, int ticksPerAction) {
        super(maxLevel);
        skillCooldown = new TickTimer(ticksPerAction);
    }

    protected abstract void onActivateSkill(ServerPlayer serverPlayer);

    public void activateSkill(ServerPlayer serverPlayer) {
        if (!isSkillReady) {
            return;
        }
        isSkillReady = false;
        onActivateSkill(serverPlayer);
    }

    public void checkSkillReady(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && !isSkillReady && skillCooldown.doTick()) {
            isSkillReady = true;
        }
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
