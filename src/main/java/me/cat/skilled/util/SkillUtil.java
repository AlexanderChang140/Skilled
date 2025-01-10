package me.cat.skilled.util;

import me.cat.skilled.capability.ISkillCap;
import me.cat.skilled.capability.SkillCap;
import me.cat.skilled.capability.SkillProvider;
import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.data.ActiveSkillData;
import me.cat.skilled.skill.instance.ActiveSkill;
import me.cat.skilled.skill.instance.Skill;
import me.cat.skilled.registry.SkillRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class SkillUtil {
    public static void syncSkillCap(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.syncCapability(serverPlayer));
    }

    public static void setPlayerLevel(ServerPlayer serverPlayer, int level) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.setPlayerLevel(level));
        syncSkillCap(serverPlayer);
    }

    public static void setSkillPoints(ServerPlayer serverPlayer, int points) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.setSkillPoints(points));
        syncSkillCap(serverPlayer);
    }

    public static void setCategoryId(ServerPlayer serverPlayer, String categoryId) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.setCategory(categoryId));
        syncSkillCap(serverPlayer);
    }

    public static void updateSkill(ServerPlayer serverPlayer, String skillId, int level) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.updateSkill(skillId, level));
        syncSkillCap(serverPlayer);
    }

    public static void clearSkills(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(ISkillCap::clearSkills);
        syncSkillCap(serverPlayer);
    }

    public static void clearAll(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(ISkillCap::clearAll);
        syncSkillCap(serverPlayer);
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

    public static boolean hasSkill(Player player, String skillId) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(skills -> skills.hasSkill(skillId))
                .orElse(false);
    }

    public static int getPlayerLevel(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getPlayerLevel)
                .orElseThrow();
    }

    public static String getCategoryId(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getCategory)
                .orElseThrow();
    }

    public static int getSkillPoints(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getSkillPoints)
                .orElseThrow();
    }

    public static int getUsedSkillPoints(Player player) {
        return Mth.clamp(getPlayerLevel(player) - getSkillPoints(player), 0, getPlayerLevel(player));
    }

    public static int getSkillLevel(Player player, String skillId) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(skills -> skills.getSkillLevel(skillId))
                .orElseThrow();
    }

    public static int getSkillMaxLevel(String skillId) {
        return SkillRegistry.getSkillData(skillId).getMaxLevel();
    }

    public static Skill getSkillInstance(Player player, String skillId) {
        return player.getCapability(SkillProvider.SKILLS)
                .resolve()
                .map(skills -> skills.getSkillInstance(skillId))
                .orElse(null);
    }

    public static boolean isActiveSkill(String skillId) {
        return SkillRegistry.getSkillData(skillId) instanceof ActiveSkillData;
    }

    public static Collection<String> getActiveSkillIds(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getActiveSkills)
                .orElse(null);
    }

    public static String getActiveSkillId(Player player, SkillSlot skillSlot) {
        return player.getCapability(SkillProvider.SKILLS)
                .resolve()
                .map(skills -> skills.getActiveSkillId(skillSlot))
                .orElse(null);
    }

    public static ActiveSkill getActiveSkillInstance(Player player, SkillSlot skillSlot) {
        String activeSkillId = player.getCapability(SkillProvider.SKILLS)
                .map(skills -> skills.getActiveSkillId(skillSlot))
                .orElse(null);
        return (ActiveSkill) SkillUtil.getSkillInstance(player, activeSkillId);
    }

    public static boolean hasActiveSkill(Player player, SkillSlot skillSlot) {
        String skillId = player.getCapability(SkillProvider.SKILLS)
                .map(skills -> skills.getActiveSkillId(skillSlot))
                .orElse(null);
        return skillId != null;
    }

    public static Map<String, Skill> getSkillMap(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getSkillMap)
                .orElseThrow();
    }

    public static Map<SkillSlot, String> getActiveSkillMap(Player player) {
        return Collections.unmodifiableMap(player.getCapability(SkillProvider.SKILLS)
                .map(SkillCap::getActiveSkillMap)
                .orElseThrow());
    }
}
