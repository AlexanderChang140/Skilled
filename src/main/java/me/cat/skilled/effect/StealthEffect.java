package me.cat.skilled.effect;

import me.cat.skilled.registry.EffectRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StealthEffect extends MobEffect {
    public static final double DETECTION_DECREASE = 0.2;

    public StealthEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getNewTarget() instanceof ServerPlayer serverPlayer && serverPlayer.hasEffect(EffectRegistry.STEALTH.get()) && serverPlayer.isCrouching()) {
            MobEffectInstance mobEffectInstance = serverPlayer.getEffect(EffectRegistry.STEALTH.get());
            double followRange = event.getEntity().getAttribute(Attributes.FOLLOW_RANGE).getValue() * (1 - DETECTION_DECREASE * mobEffectInstance.getAmplifier());
            if (event.getTargetType() == LivingChangeTargetEvent.LivingTargetType.MOB_TARGET && (event.getEntity().position().distanceTo(serverPlayer.position()) > followRange)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer && serverPlayer.hasEffect(EffectRegistry.STEALTH.get())) {
            serverPlayer.removeEffect(EffectRegistry.STEALTH.get());
        }
    }
}
