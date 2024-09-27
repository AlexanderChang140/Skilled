package me.cat.skilled.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectUtil {

    public static void incrementEffect(LivingEntity livingEntity, MobEffect effect) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier + 1));
    }

    public static void incrementEffect(LivingEntity livingEntity, MobEffect effect, int amount) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier + amount));
    }

    public static void decrementEffect(LivingEntity livingEntity, MobEffect effect) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        if (amplifier > 0) {
            livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier - 1));
        }
    }

    public static void decrementEffect(LivingEntity livingEntity, MobEffect effect, int amount) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        if (amplifier > 0) {
            livingEntity.addEffect(new MobEffectInstance(effect, duration, Math.max(amplifier - amount, 0)));
        }
    }
}
