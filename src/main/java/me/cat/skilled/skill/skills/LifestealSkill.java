package me.cat.skilled.skill.skills;

import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class LifestealSkill extends Skill {
    private final static float HEAL_PERCENT = 0.25F;

    public void onLivingAttackEvent(LivingAttackEvent event) {
        ServerPlayer serverPlayer = (ServerPlayer) event.getEntity();
        serverPlayer.heal(event.getAmount() * HEAL_PERCENT);
    }
}
