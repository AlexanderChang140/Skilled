package me.cat.skilled.skill.instance.warrior.passive;

import me.cat.skilled.skill.instance.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LifestealSkill extends Skill {
    public static final int MAX_LEVEL = 5;
    private final static float HEAL_PERCENT = 0.25F;

    public LifestealSkill() {
        super();
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @SubscribeEvent
    public static void onLivingAttackEvent(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.hasSkill(serverPlayer, SkillRegistry.LIFESTEAL.getSkillId()))) {
            return;
        }

        serverPlayer.heal(event.getAmount() * HEAL_PERCENT);
    }
}
