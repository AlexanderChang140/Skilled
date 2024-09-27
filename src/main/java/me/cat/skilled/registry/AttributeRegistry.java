package me.cat.skilled.registry;

import me.cat.skilled.Skilled;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Skilled.MODID);

    public static final RegistryObject<Attribute> BARRIER_LEVEL = ATTRIBUTES.register("barrier_level",
            () -> new RangedAttribute("barrier_level", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> LIFESTEAL = ATTRIBUTES.register("lifesteal",
            () -> new RangedAttribute("lifesteal", 0.0D, 0.0D, 1024.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
