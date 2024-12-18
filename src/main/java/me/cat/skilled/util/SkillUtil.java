package me.cat.skilled.util;

import me.cat.skilled.capability.PlayerSkills;
import me.cat.skilled.capability.PlayerSkillsProvider;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.skill.Skill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillUtil {
    public static void syncCapability(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .ifPresent(skills -> skills.syncCapability(serverPlayer));
    }

    public static boolean hasSkill(Player player, String skillId) {
        return player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .map(skills -> skills.hasSkill(skillId))
                .orElse(false);
    }

    public static boolean isActiveSkill(Player player, String skillId) {
        return player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .map(skills -> skills.getSkillInstance(skillId) instanceof  ActiveSkill)
                .orElse(false);
    }

    public static void updateSkill(Player player, String skillId, int level) {
        player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .ifPresent(skills -> skills.updateSkill(skillId, level));
    }

    public static int getSkillLevel(Player player, String skillId) {
        return player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .map(skills -> skills.getSkillLevel(skillId))
                .orElseThrow();
    }

    public static Skill getSkillInstance(Player player, String skillId) {
        return player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .resolve()
                .map(skills -> skills.getSkillInstance(skillId))
                .orElse(null);
    }

    public static String getPrimarySkillId(Player player) {
        return player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .map(PlayerSkills::getPrimarySkillId)
                .orElse(null);
    }

    public static void setPrimarySkillId(Player player, String skillId) {
        player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .ifPresent(skills -> skills.setPrimarySkillId(skillId));
    }

    public static ActiveSkill getPrimarySkillInstance(Player player) {
        String primarySkillId = SkillUtil.getPrimarySkillId(player);
        if (primarySkillId != null && SkillUtil.getSkillInstance(player, primarySkillId) instanceof ActiveSkill activeSkill) {
            return activeSkill;
        }
        return null;
    }

    public static Map<String, Skill> getMap(Player player) {
        return player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                .map(PlayerSkills::getMap)
                .orElseThrow();
    }

    public static List<String> getSkillIds() {
        List<String> stringFields = new ArrayList<>();

        Field[] fields = SkillIds.class.getFields();

        for (Field field : fields) {
            if (field.getType() == String.class) {
                try {
                    stringFields.add((String) field.get(null));
                } catch (IllegalAccessException ignored) {
                    System.out.println("Failed to get skill id");
                }
            }
        }
        return stringFields;
    }
}
