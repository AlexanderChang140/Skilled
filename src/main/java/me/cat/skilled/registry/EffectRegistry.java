package me.cat.skilled.registry;

import me.cat.skilled.Skilled;
import me.cat.skilled.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.puffish.attributesmod.AttributesMod;

public class EffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Skilled.MODID);

    public static final RegistryObject<MobEffect> BARRIER = MOB_EFFECTS.register("barrier",
            () -> new BarrierEffect(MobEffectCategory.BENEFICIAL, 5636095));

    public static final RegistryObject<MobEffect> STEALTH = MOB_EFFECTS.register("stealth",
            () -> new StealthEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final RegistryObject<MobEffect> MARKED = MOB_EFFECTS.register("marked",
            () -> new MarkedEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<MobEffect> FRENZY = MOB_EFFECTS.register("frenzy",
            () -> new FrenzyEffect(MobEffectCategory.BENEFICIAL, 0)
                    .addAttributeModifier(
                            Attributes.ATTACK_SPEED,
                            FrenzyEffect.FRENZY_ATTACK_SPEED_UUID.toString(),
                            FrenzyEffect.FRENZY_ATTACK_SPEED_INCREASE,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    ));

    public static final RegistryObject<MobEffect> BLOODLUST = MOB_EFFECTS.register("bloodlust",
            () -> new BloodlustEffect(MobEffectCategory.BENEFICIAL, 0)
                    .addAttributeModifier(
                            Attributes.ATTACK_DAMAGE,
                            BloodlustEffect.BLOODLUST_ATTACK_DAMAGE_MULTIPLIER_UUID.toString(),
                            BloodlustEffect.BLOODLUST_ATTACK_DAMAGE_MULTIPLIER,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    ));

    public static final RegistryObject<MobEffect> IMMOBILIZED = MOB_EFFECTS.register("immobilized",
            () -> new ImmobilizedEffect(MobEffectCategory.HARMFUL, 0)
                    .addAttributeModifier(
                            Attributes.MOVEMENT_SPEED,
                            ImmobilizedEffect.IMMOBILIZED_MOVEMENT_SPEED_UUID.toString(),
                            ImmobilizedEffect.IMMOBILIZED_MOVEMENT_SPEED_DECREASE,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    ));

    public static final RegistryObject<MobEffect> PIERCING_MOMENTUM = MOB_EFFECTS.register("piercing_momentum",
            () -> new PiercingMomentumEffect(MobEffectCategory.BENEFICIAL, 0)
                    .addAttributeModifier(
                            AttributesMod.RANGED_DAMAGE,
                            PiercingMomentumEffect.PIERCING_MOMENTUM_UUID.toString(),
                            PiercingMomentumEffect.DAMAGE_MULTIPLIER,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    ));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}