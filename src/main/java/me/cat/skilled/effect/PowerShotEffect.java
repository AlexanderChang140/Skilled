package me.cat.skilled.effect;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.util.EffectUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PowerShotEffect extends MobEffect {
    private static final double VELOCITY_MULTIPLIER = 1.25;
    private static final double START_DAMAGE_MULTIPLIER = 1.15;
    private static final double PER_LEVEL_ADDITIVE = 0.15F;

    public PowerShotEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Mod.EventBusSubscriber
    public static class EffectEvents {
        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            if (event.getEntity() instanceof Arrow arrow
                    && arrow.getOwner() instanceof ServerPlayer serverPlayer
                    && serverPlayer.hasEffect(EffectRegistry.POWER_SHOT.get())) {
                var mobEffectInstance = serverPlayer.getEffect(EffectRegistry.POWER_SHOT.get());
                arrow.setDeltaMovement(arrow.getDeltaMovement().scale(VELOCITY_MULTIPLIER));
                arrow.hasImpulse = true;
                arrow.setBaseDamage(arrow.getBaseDamage() * (START_DAMAGE_MULTIPLIER + PER_LEVEL_ADDITIVE * mobEffectInstance.getAmplifier()));
                EffectUtil.decrementEffect(serverPlayer, EffectRegistry.POWER_SHOT.get());
            }
        }
    }
}
