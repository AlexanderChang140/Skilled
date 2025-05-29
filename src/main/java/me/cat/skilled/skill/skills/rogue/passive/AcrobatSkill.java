package me.cat.skilled.skill.skills.rogue.passive;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AcrobatSkill extends Skill {
    @Mod.EventBusSubscriber
    public static class SlowFallEventHandler {
        @SubscribeEvent
        public static void onLivingDamage(LivingDamageEvent event) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.hasSkill(serverPlayer, SkillRegistry.SLOW_FALL.getSkillId())
                    && event.getSource().is(DamageTypes.FALL)) {
                event.setAmount(event.getAmount() * 0.5f);
            }
        }
    }
}
