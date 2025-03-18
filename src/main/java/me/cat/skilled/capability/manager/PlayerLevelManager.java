package me.cat.skilled.capability.manager;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.ISkillCap;
import me.cat.skilled.capability.SkillCap;
import me.cat.skilled.capability.SkillProvider;
import me.cat.skilled.experience.ExperienceUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class PlayerLevelManager {
    public static void addPlayerLevel(ServerPlayer serverPlayer, int level) {
        int newLevel = Math.min(SkillCap.MAX_LEVEL, getPlayerLevel(serverPlayer) + level);
        updatePlayerLevel(serverPlayer, newLevel);
    }

    public static void updatePlayerLevel(ServerPlayer serverPlayer, int level) {
        setPlayerLevel(serverPlayer, level);
        setPlayerExperience(serverPlayer, ExperienceUtil.levelToExperience(level));
        SyncManager.syncSkillCap(serverPlayer);
    }

    public static void addPlayerExperience(ServerPlayer serverPlayer, int experience) {
        int newExperience = Math.min(ExperienceUtil.levelToExperience(SkillCap.MAX_LEVEL), getPlayerExperience(serverPlayer) + experience);
        updatePlayerExperience(serverPlayer, newExperience);
    }

    public static void updatePlayerExperience(ServerPlayer serverPlayer, int experience) {
        setPlayerLevel(serverPlayer, ExperienceUtil.experienceToLevel(experience));
        setPlayerExperience(serverPlayer, experience);
        SyncManager.syncSkillCap(serverPlayer);
    }

    public static void addSkillPoints(ServerPlayer serverPlayer, int points) {
        setSkillPoints(serverPlayer, getSkillPoints(serverPlayer) + points);
        SyncManager.syncSkillCap(serverPlayer);
    }

    public static int getPlayerLevel(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getPlayerLevel)
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve player level for " + player.getName());
                    return -1;
                });
    }

    private static void setPlayerLevel(ServerPlayer serverPlayer, int level) {
        int currentLevel = getPlayerLevel(serverPlayer);
        int skillPoints = level - currentLevel;
        if (skillPoints > 0) {
            onLevelUp(serverPlayer, currentLevel, level);
        }
        setSkillPoints(serverPlayer, getSkillPoints(serverPlayer) + skillPoints);
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.setPlayerLevel(Mth.clamp(level, 1, SkillCap.MAX_LEVEL)));
    }

    private static void onLevelUp(ServerPlayer serverPlayer, int previousLevel, int currentLevel) {
        Component component = Component.literal(String.format("You leveled up! (%d -> %d)", previousLevel, currentLevel));
        serverPlayer.sendSystemMessage(component);
        serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static int getPlayerExperience(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(SkillCap::getPlayerExperience)
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve experience for " + player.getName());
                    return -1;
                });
    }

    private static void setPlayerExperience(ServerPlayer serverPlayer, int experience) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.setPlayerExperience(experience));
    }

    public static int getSkillPoints(Player player) {
        return player.getCapability(SkillProvider.SKILLS)
                .map(ISkillCap::getSkillPoints)
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve skill points for " + player.getName());
                    return -1;
                });
    }

    private static void setSkillPoints(ServerPlayer serverPlayer, int points) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.setSkillPoints(points));
    }

    public static int getUsedSkillPoints(Player player) {
        return Mth.clamp(getPlayerLevel(player) - getSkillPoints(player), 0, getPlayerLevel(player));
    }
}
