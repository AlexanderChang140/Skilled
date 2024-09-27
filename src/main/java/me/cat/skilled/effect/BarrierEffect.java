package me.cat.skilled.effect;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.util.EffectUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BarrierEffect extends MobEffect {
    public BarrierEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Mod.EventBusSubscriber
    public static class EffectEvents {
        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            LivingEntity livingEntity = event.getEntity();

            if (livingEntity.level().isClientSide() ||
                    !livingEntity.hasEffect(EffectRegistry.BARRIER.get())) {
                return;
            }

            event.setCanceled(true);
            EffectUtil.decrementEffect(livingEntity, EffectRegistry.BARRIER.get());
        }
    }
}
