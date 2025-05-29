package me.cat.skilled.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.UUID;

public class BloodlustEffect extends MobEffect {
    public static final double BLOODLUST_ATTACK_DAMAGE_MULTIPLIER = 0.10;
    public static final UUID BLOODLUST_ATTACK_DAMAGE_MULTIPLIER_UUID = UUID.nameUUIDFromBytes("bloodlust_attack_damage_multiplier".getBytes());

    public BloodlustEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
