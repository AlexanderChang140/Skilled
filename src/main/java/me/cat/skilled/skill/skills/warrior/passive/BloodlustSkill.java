package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.EffectUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BloodlustSkill extends Skill {
    private static final int EFFECT_DURATION = 200;
    private static final int MAX_STACKS = 5;

    @Mod.EventBusSubscriber
    public static class BloodlustEventHandler {
        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            if (!(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)) return;
            if (!(PlayerSkillManager.hasSkill(serverPlayer, SkillRegistry.BLOODLUST.getSkillId()))) return;
            EffectUtil.stackEffect(serverPlayer, true, MAX_STACKS, EffectRegistry.BLOODLUST.get(), EFFECT_DURATION, 1, false, false);
        }
    }
}
