package me.cat.skilled.util;

import me.cat.skilled.capability.PlayerSkillsProvider;
import net.minecraft.world.entity.player.Player;

public class SkillUtil {
    public static boolean hasSkill(Player player, String skillId) {
        return player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .map(skills -> skills.hasSkill(skillId))
                .orElse(false);
    }

    public static int getSkillLevel(Player player, String skillID) {
        return player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .map(skills -> skills.getSkillLevel(skillID))
                .orElse(0);
    }
}
