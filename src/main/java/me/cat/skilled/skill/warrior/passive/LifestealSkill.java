package me.cat.skilled.skill.warrior.passive;

import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LifestealSkill extends Skill {
    private final static float HEAL_PERCENT = 0.25F;

    public LifestealSkill() {
        super(1);
    }

    @SubscribeEvent
    public static void onLivingAttackEvent(LivingAttackEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer && SkillUtil.hasSkill(serverPlayer, SkillIds.LIFESTEAL)) {
            serverPlayer.heal(event.getAmount() * HEAL_PERCENT);
        }
    }
}
