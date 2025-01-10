package me.cat.skilled.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.UUID;

public class PiercingMomentumEffect extends MobEffect {
    public static final double DAMAGE_MULTIPLIER = 0.05D;
    public static final UUID PIERCING_MOMENTUM_UUID = UUID.nameUUIDFromBytes("piercing_momentum".getBytes());

    public PiercingMomentumEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}