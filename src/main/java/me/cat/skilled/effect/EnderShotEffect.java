package me.cat.skilled.effect;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.util.EffectUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EnderShotEffect extends MobEffect {
    public EnderShotEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Mod.EventBusSubscriber
    public static class EffectEvents {
        @SubscribeEvent
        public static void onArrowImpact(ProjectileImpactEvent event) {
            if (event.getEntity() instanceof Arrow arrow
                    && arrow.getOwner() instanceof ServerPlayer serverPlayer
                    && serverPlayer.hasEffect(EffectRegistry.ENDER_SHOT.get())) {
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
                serverPlayer.teleportTo(arrow.getBlockX(), arrow.getBlockY(), arrow.getBlockZ());
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
                EffectUtil.decrementEffect(serverPlayer, EffectRegistry.ENDER_SHOT.get());
            }
        }
    }
}
