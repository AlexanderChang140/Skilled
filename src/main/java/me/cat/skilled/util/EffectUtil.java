package me.cat.skilled.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class EffectUtil {

    public static void setEffectDuration(LivingEntity livingEntity, MobEffect effect, int duration) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
    }

    public static void incrementEffect(LivingEntity livingEntity, MobEffect effect) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier + 1, false, false));
    }

    public static void incrementEffect(LivingEntity livingEntity, MobEffect effect, int amount) {
        if (!livingEntity.hasEffect(effect) || amount <= 0) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier + amount, false, false));
    }

    public static void incrementEffect(LivingEntity livingEntity, MobEffect effect, int amount, boolean ambient, boolean visible) {
        if (!livingEntity.hasEffect(effect) || amount <= 0) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier + amount, ambient, visible));
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
            livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier - 1, false, false));
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
            livingEntity.addEffect(new MobEffectInstance(effect, duration, Math.max(amplifier - amount, 0), false, false));
        }
    }

    public static void decrementEffect(LivingEntity livingEntity, MobEffect effect, int amount, boolean ambient, boolean visible) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();

        livingEntity.removeEffect(effect);
        if (amplifier > 0) {
            livingEntity.addEffect(new MobEffectInstance(effect, duration, Math.max(amplifier - amount, 0), ambient, visible));
        }
    }
}
