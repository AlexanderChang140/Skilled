package me.cat.skilled.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.UUID;

public class FrenzyEffect extends MobEffect {
    public static final double FRENZY_ATTACK_SPEED_INCREASE = 0.05D;
    public static final UUID FRENZY_ATTACK_SPEED_UUID = UUID.nameUUIDFromBytes("frenzy_attack_speed".getBytes());

    public FrenzyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}



