package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LifestealSkill extends Skill {
    private final static float HEAL_PERCENT = 0.25F;

    public static float getHealPercent() {
        return HEAL_PERCENT;
    }

    @SubscribeEvent
    public static void onLivingAttackEvent(LivingAttackEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer serverPlayer)) return;
        if (!(PlayerSkillManager.hasSkill(serverPlayer, SkillRegistry.LIFESTEAL.getSkillId()))) return;
        serverPlayer.heal(event.getAmount() * getHealPercent());
    }
}
