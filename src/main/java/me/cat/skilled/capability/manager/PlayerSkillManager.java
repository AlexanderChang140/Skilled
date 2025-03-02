package me.cat.skilled.capability.manager;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.ISkillCap;
import me.cat.skilled.capability.SkillCap;
import me.cat.skilled.capability.SkillProvider;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.registry.SkillRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class PlayerSkillManager {

    public static void updateSkillLevel(ServerPlayer serverPlayer, String skillId, int level) {
        level = Mth.clamp(level, 0, PlayerSkillManager.getSkillMaxLevel(skillId));
        int currLevel = PlayerSkillManager.getSkillLevel(serverPlayer, skillId);
        int skillPoints = currLevel - level;
        setSkillLevel(serverPlayer, skillId, level);
        PlayerLevelManager.addSkillPoints(serverPlayer, skillPoints);
    }

    public static void clearSkills(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(ISkillCap::clearSkills);
        SyncManager.syncSkillCap(serverPlayer);
    }

    public static void clearAll(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(ISkillCap::clearAll);
        SyncManager.syncSkillCap(serverPlayer);
    }

    public static String getCategoryId(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getCategory)
                .orElseThrow();
    }

    public static void setCategoryId(ServerPlayer serverPlayer, String categoryId) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.setCategory(categoryId));
        SyncManager.syncSkillCap(serverPlayer);
    }

    public static int getSkillLevel(Player player, String skillId) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(skills -> skills.getSkillLevel(skillId))
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve skill level for player: " + player.getName() + " skill: " + skillId);
                    return 0;
                });
    }

    private static void setSkillLevel(ServerPlayer serverPlayer, String skillId, int level) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.updateSkill(skillId, level));
    }

    public static boolean hasSkill(Player player, String skillId) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(skills -> skills.hasSkill(skillId))
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve has skill for player: " + player.getName() + " skill: " + skillId);
                    return false;
                });
    }

    public static Skill getSkillInstance(Player player, String skillId) {
        return player.getCapability(SkillProvider.SKILLS)
                .resolve()
                .map(skills -> skills.getSkillInstance(skillId))
                .orElse(null);
    }

    public static int getSkillMaxLevel(String skillId) {
        return SkillRegistry.getSkillData(skillId).getMaxLevel();
    }

    public static boolean skillExists(String skillId) {
        return SkillRegistry.getSkillIds().contains(skillId);
    }

    public static boolean hasPrerequisites(Player player, String skillId) {
        for (String prereqId : SkillRegistry.getSkillData(skillId).getPrerequisites()) {
            if (!hasSkill(player, prereqId)) {
                return false;
            }
        }
        return true;
    }

    public static Map<String, Skill> getSkillMap(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getSkillMap)
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve skill map for player: " + player.getName());
                    return null;
                });
    }

    public static boolean isActiveSkill(String skillId) {
        return SkillRegistry.getSkillData(skillId) instanceof ActiveSkillData;
    }

    public static Collection<String> getActiveSkillIds(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getActiveSkills)
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve active skill ids for player: " + player.getName());
                    return null;
                });
    }

    public static String getActiveSkillId(Player player, SkillSlot skillSlot) {
        return player.getCapability(SkillProvider.SKILLS)
                .resolve()
                .map(skills -> skills.getActiveSkillId(skillSlot))
                .orElse(null);
    }

    public static ActiveSkill getActiveSkillInstance(Player player, SkillSlot skillSlot) {
        String activeSkillId = player.getCapability(SkillProvider.SKILLS)
                .resolve()
                .map(skills -> skills.getActiveSkillId(skillSlot))
                .orElse(null);
        return activeSkillId != null ? (ActiveSkill) PlayerSkillManager.getSkillInstance(player, activeSkillId) : null;
    }

    public static boolean hasActiveSkill(Player player, SkillSlot skillSlot) {
        String skillId = player.getCapability(SkillProvider.SKILLS)
                .map(skills -> skills.getActiveSkillId(skillSlot))
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve has active skill for player: " + player.getName() + " slot: " + skillSlot.toString());
                    return null;
                });
        return skillId != null;
    }

    public static Map<SkillSlot, String> getActiveSkillMap(Player player) {
        return Collections.unmodifiableMap(Objects.requireNonNull(player.getCapability(SkillProvider.SKILLS)
                .map(SkillCap::getActiveSkillMap)
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve has active skill map for player: " + player.getName());
                    return null;
                })));
    }
}