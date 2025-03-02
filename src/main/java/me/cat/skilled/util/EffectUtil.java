package me.cat.skilled.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectUtil {
    public static void setEffectDuration(LivingEntity livingEntity, MobEffect effect, int duration) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int amplifier = mobEffectInstance.getAmplifier();
        boolean ambient = mobEffectInstance.isAmbient();
        boolean visible = mobEffectInstance.isVisible();

        livingEntity.removeEffect(effect);
        livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier, ambient, visible));
    }

    public static void stackEffect(LivingEntity livingEntity, boolean isDurationReset, int maxStacks, MobEffect effect, int duration, int amplifier, boolean ambient, boolean visible) {
        if (livingEntity.hasEffect(effect) ) {
            if (livingEntity.getEffect(effect).getAmplifier() < maxStacks - 1) {
                EffectUtil.incrementEffect(livingEntity, effect, amplifier + 1);
            }
            if (isDurationReset) {
                EffectUtil.setEffectDuration(livingEntity, effect, duration);
            }
        }
        else {
            MobEffectInstance mobEffectInstance = new MobEffectInstance(effect, duration, amplifier, ambient, visible);
            livingEntity.addEffect(mobEffectInstance);
        }
    }

    public static void incrementEffect(LivingEntity livingEntity, MobEffect effect) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();
        boolean ambient = mobEffectInstance.isAmbient();
        boolean visible = mobEffectInstance.isVisible();

        livingEntity.removeEffect(effect);
        livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier + 1, ambient, visible));
    }

    public static void incrementEffect(LivingEntity livingEntity, MobEffect effect, int amount) {
        if (!livingEntity.hasEffect(effect) || amount <= 0) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();
        boolean ambient = mobEffectInstance.isAmbient();
        boolean visible = mobEffectInstance.isVisible();

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
        boolean ambient = mobEffectInstance.isAmbient();
        boolean visible = mobEffectInstance.isVisible();

        livingEntity.removeEffect(effect);
        if (amplifier > 0) {
            livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier - 1, ambient, visible));
        }
    }

    public static void decrementEffect(LivingEntity livingEntity, MobEffect effect, int amount) {
        if (!livingEntity.hasEffect(effect)) {
            return;
        }

        MobEffectInstance mobEffectInstance = livingEntity.getEffect(effect);
        int duration = mobEffectInstance.getDuration();
        int amplifier = mobEffectInstance.getAmplifier();
        boolean ambient = mobEffectInstance.isAmbient();
        boolean visible = mobEffectInstance.isVisible();

        livingEntity.removeEffect(effect);
        if (amplifier > 0) {
            livingEntity.addEffect(new MobEffectInstance(effect, duration, Math.max(amplifier - amount, 0), ambient, visible));
        }
    }
}
