package me.cat.skilled.skill.ranger.active;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.TickTimer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

public class EnderShotSkill extends ActiveSkill {
    private final TickTimer toggleTimer = new TickTimer(20);
    private boolean canToggle = true;
    private boolean isToggled = false;

    public static final int MAX_LEVEL = 1;

    public EnderShotSkill() {
        super(200, 1);
    }

    @Override
    public boolean onActivateSkill(ServerPlayer serverPlayer) {
        if (canToggle) {
            isToggled = !isToggled;
            canToggle = false;
        }
        return false;
    }

    @Mod.EventBusSubscriber
    public static class EnderShotEvents {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (PlayerSkillManager.getSkillInstance(event.player, SkillRegistry.ENDER_SHOT.getSkillId()) instanceof EnderShotSkill skill
                    && !skill.canToggle
                    && skill.toggleTimer.doTick()) {
                skill.canToggle = true;
            }
        }

        @SubscribeEvent
        public static void onArrowImpact(ProjectileImpactEvent event) {
            if (event.getEntity() instanceof Arrow arrow
                    && arrow.getOwner() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.ENDER_SHOT.getSkillId()) instanceof EnderShotSkill skill
                    && skill.isToggled) {
                skill.isToggled = false;
                skill.isSkillReady = false;
                generateParticles(serverPlayer);
                serverPlayer.teleportTo(arrow.getBlockX(), arrow.getBlockY(), arrow.getBlockZ());
                generateParticles(serverPlayer);
            }

        }

        private static void generateParticles(ServerPlayer serverPlayer) {
            serverPlayer.serverLevel().sendParticles(
                    ParticleTypes.PORTAL,
                    serverPlayer.getX(),
                    serverPlayer.getY(),
                    serverPlayer.getZ(),
                    20,
                    0.5,
                    1.0,
                    0.5,
                    0.1);
        }
    }

    @Override
    public CompoundTag saveNbt() {
        CompoundTag tag = super.saveNbt();
        tag.putInt("toggle_timer", toggleTimer.getTickCounter());
        tag.putBoolean("can_toggle", canToggle);
        tag.putBoolean("is_toggled", isToggled);
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        super.loadNbt(tag);
        toggleTimer.setTickCounter(tag.getInt("toggle_timer"));
        canToggle = tag.getBoolean("can_toggle");
        isToggled = tag.getBoolean("is_toggled");
    }

    @Override
    public int getCurrTick() {
        return isToggled ? -1 : super.getCurrTick();
    }

    @Override
    public boolean showCooldown() {
        return isToggled || !isSkillReady;
    }

    @Override
    public Color getCooldownColorValues() {
        return isToggled ? Color.YELLOW : super.getCooldownColorValues();
    }
}
