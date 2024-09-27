package me.cat.skilled.skill;

import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LifestealSkill {
    final static float HEAL_PERCENT = 0.25F;

    @SubscribeEvent
    public static void onLivingAttackEvent(LivingAttackEvent event) {
        if (event.getSource().getDirectEntity() instanceof ServerPlayer serverPlayer && SkillUtil.hasSkill(serverPlayer, SkillIds.LIFESTEAL_SKILL)) {
            serverPlayer.heal(event.getAmount() * HEAL_PERCENT);
        }
    }
}
