package me.cat.skilled.registry;

import me.cat.skilled.Skilled;
import me.cat.skilled.effect.BarrierEffect;
import me.cat.skilled.effect.FrenzyEffect;
import me.cat.skilled.effect.PowerShotEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.cat.skilled.effect.FrenzyEffect.FRENZY_ATTACK_SPEED_INCREASE;
import static me.cat.skilled.effect.FrenzyEffect.FRENZY_ATTACK_SPEED_UUID;

public class EffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Skilled.MODID);

    public static final RegistryObject<MobEffect> BARRIER = MOB_EFFECTS.register("barrier",
            () -> new BarrierEffect(MobEffectCategory.BENEFICIAL, 5636095));

    public static final RegistryObject<MobEffect> POWER_SHOT = MOB_EFFECTS.register("power_shot",
            () -> new PowerShotEffect(MobEffectCategory.BENEFICIAL, 16744576));

    public static final RegistryObject<MobEffect> ENDER_SHOT = MOB_EFFECTS.register("ender_shot",
            () -> new PowerShotEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final RegistryObject<MobEffect> FRENZY = MOB_EFFECTS.register("frenzy",
            () -> new FrenzyEffect(MobEffectCategory.BENEFICIAL, 0)
                .addAttributeModifier(
                        Attributes.ATTACK_SPEED,
                        FRENZY_ATTACK_SPEED_UUID.toString(),
                        FRENZY_ATTACK_SPEED_INCREASE,
                        AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
