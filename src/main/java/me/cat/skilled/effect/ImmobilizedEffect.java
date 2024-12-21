package me.cat.skilled.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.UUID;

public class ImmobilizedEffect extends MobEffect {

    public static final double IMMOBILIZED_MOVEMENT_SPEED_DECREASE = -0.10D;
    public static final UUID IMMOBILIZED_MOVEMENT_SPEED_UUID = UUID.nameUUIDFromBytes("immobilized_movement_speed".getBytes());

    public ImmobilizedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
