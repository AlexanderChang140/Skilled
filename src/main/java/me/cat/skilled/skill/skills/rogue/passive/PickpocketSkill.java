package me.cat.skilled.skill.skills.rogue.passive;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PickpocketSkill extends Skill {
    @Mod.EventBusSubscriber
    public static class PickpocketEventHandler {
        @SubscribeEvent
        public static void onLootingLevelEvent(LootingLevelEvent event) {
            if (event.getDamageSource().getEntity() instanceof ServerPlayer serverPlayer && PlayerSkillManager.hasSkill(serverPlayer, SkillRegistry.PICKPOCKET.getSkillId())) {
                event.setLootingLevel(event.getLootingLevel() + 1);
            }
        }
    }
}
