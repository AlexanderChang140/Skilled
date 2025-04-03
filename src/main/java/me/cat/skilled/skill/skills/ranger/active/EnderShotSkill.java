package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.capability.manager.SyncManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.ToggleableSkill;
import me.cat.skilled.util.event.Event;
import me.cat.skilled.util.event.EventHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EnderShotSkill extends ToggleableSkill {
    private final Event<Void, Void> onTeleport = new Event<>(callback -> { });
    private final EventHandler<Void, Void> onTeleportHandler = new EventHandler<>(onTeleport);

    public EnderShotSkill() {
        super(200);
    }

    @Mod.EventBusSubscriber
    public static class EnderShotEventHandler {
        @SubscribeEvent
        public static void onArrowImpact(ProjectileImpactEvent event) {
            if (event.getEntity() instanceof Arrow arrow
                    && arrow.getOwner() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.ENDER_SHOT.getSkillId()) instanceof EnderShotSkill skill
                    && skill.isToggled()) {
                skill.consume();
                SyncManager.syncSkill(serverPlayer, skill);
                generateParticles(serverPlayer);
                serverPlayer.teleportTo(arrow.getBlockX(), arrow.getBlockY(), arrow.getBlockZ());
                skill.onTeleport.notifySubscribers(null);
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

    public EventHandler<Void, Void> getTeleportEvent() {
        return onTeleportHandler;
    }
}
