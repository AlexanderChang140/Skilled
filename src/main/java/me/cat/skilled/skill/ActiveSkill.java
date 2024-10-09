package me.cat.skilled.skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;

public abstract class ActiveSkill extends Skill {
    private boolean isSkillReady = true;
    private int tickCounter = 0;
    protected final int TICKS_PER_ACTION;

    protected ActiveSkill(int ticksPerAction) {
        TICKS_PER_ACTION = ticksPerAction;
    }

    protected abstract void onActivateSkill(ServerPlayer serverPlayer);

    public void activateSkill(ServerPlayer serverPlayer) {
        if (!isSkillReady) {
            return;
        }
        isSkillReady = false;
        onActivateSkill(serverPlayer);
    }

    private boolean doTick() {
        tickCounter++;
        if (tickCounter >= TICKS_PER_ACTION) {
            tickCounter = 0;
            return true;
        }
        return false;
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && !isSkillReady && doTick()) {
            isSkillReady = true;
        }
    }
}
