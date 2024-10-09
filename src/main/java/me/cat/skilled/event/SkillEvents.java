package me.cat.skilled.event;

import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.skill.skills.*;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SkillEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer) {
            ActiveSkill activeSkill = SkillUtil.getPrimarySkillInstance(serverPlayer);
            if (activeSkill != null) {
                activeSkill.onPlayerTick(event);
            }
            if (SkillUtil.getSkillInstance(serverPlayer, SkillIds.BARRIER) instanceof BarrierSkill barrierSkill) {
                barrierSkill.onPlayerTick(event);
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (SkillUtil.getSkillInstance(serverPlayer, SkillIds.FRENZY) instanceof FrenzySkill frenzySkill) {
                frenzySkill.onAttackEntity(event);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (SkillUtil.getSkillInstance(serverPlayer, SkillIds.PARRY) instanceof ParrySkill parrySkill) {
                parrySkill.onLivingAttack(event);
            }

            if (SkillUtil.getSkillInstance(serverPlayer, SkillIds.LIFESTEAL) instanceof LifestealSkill lifestealSkill) {
                lifestealSkill.onLivingAttackEvent(event);
            }
        }
    }
}
