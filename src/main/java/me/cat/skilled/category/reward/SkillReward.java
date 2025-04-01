package me.cat.skilled.category.reward;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.server.level.ServerPlayer;

public class SkillReward extends Reward {
    private final String skillId;

    public SkillReward(String skillId) {
        this.skillId = skillId;
    }

    @Override
    public void onAdd(ServerPlayer serverPlayer, int level) {
        PlayerSkillManager.updateSkillLevel(serverPlayer, skillId, level);
    }

    @Override
    public void onRemove(ServerPlayer serverPlayer, int level) {
        PlayerSkillManager.updateSkillLevel(serverPlayer, skillId, 0);
    }

    public String getSkillId() {
        return skillId;
    }
}
