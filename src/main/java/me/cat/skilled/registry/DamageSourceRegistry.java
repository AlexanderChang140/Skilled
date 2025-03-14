package me.cat.skilled.registry;

import me.cat.skilled.Skilled;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class DamageSourceRegistry {
    public static final ResourceKey<DamageType> HOLY =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Skilled.MODID, "data/damage_type/holy.json"));

    public static DamageSource holyDamage(Entity causer) {
        return new DamageSource(
                causer.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(HOLY),
                causer);
    }
}
