package me.cat.skilled.event;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.AttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skilled.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AttributeRegistry.BARRIER_LEVEL.get());
        event.add(EntityType.PLAYER, AttributeRegistry.LIFESTEAL.get());
    }
}
