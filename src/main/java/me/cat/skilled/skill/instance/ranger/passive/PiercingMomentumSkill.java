package me.cat.skilled.skill.instance.ranger.passive;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.instance.Skill;
import me.cat.skilled.util.EffectUtil;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class PiercingMomentumSkill extends Skill {
    public static final int MAX_LEVEL = 1;

    private static final int EFFECT_DURATION = 100;
    private static final int MAX_STACKS = 5;

    public final List<Projectile> projectileList = new ArrayList<>();

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static int getMaxStacks() {
        return MAX_STACKS;
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            if (!(event.getEntity() instanceof Projectile projectile)) {
                return;
            }

            if (!(projectile.getOwner() instanceof ServerPlayer serverPlayer)) {
                return;
            }

            if (!(SkillUtil.getSkillInstance(serverPlayer, SkillRegistry.PIERCING_MOMENTUM.getSkillId()) instanceof PiercingMomentumSkill skill)) {
                return;
            }

            skill.projectileList.add(projectile);
        }

        @SubscribeEvent
        public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
            if (!(event.getEntity() instanceof Projectile projectile)) {
                return;
            }

            if (!(projectile.getOwner() instanceof ServerPlayer serverPlayer)) {
                return;
            }

            if (!(SkillUtil.getSkillInstance(serverPlayer, SkillRegistry.PIERCING_MOMENTUM.getSkillId()) instanceof PiercingMomentumSkill skill)) {
                return;
            }

            skill.projectileList.remove(projectile);
        }

        @SubscribeEvent
        public static void onProjectileImpactEvent(ProjectileImpactEvent event) {
            if (!(event.getEntity() instanceof Projectile projectile)) {
                return;
            }

            if (!(projectile.getOwner() instanceof ServerPlayer serverPlayer)) {
                return;
            }

            if (!(SkillUtil.getSkillInstance(serverPlayer, SkillRegistry.PIERCING_MOMENTUM.getSkillId()) instanceof PiercingMomentumSkill skill)) {
                return;
            }

            if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY) {
                return;
            }

            serverPlayer.removeEffect(EffectRegistry.PIERCING_MOMENTUM.get());
            skill.projectileList.remove(projectile);
        }

        @SubscribeEvent
        public static void onPlayerAttack(LivingAttackEvent event) {
            if (!(event.getSource().getDirectEntity() instanceof Projectile projectile)) {
                return;
            }

            if (!(projectile.getOwner() instanceof ServerPlayer serverPlayer)) {
                return;
            }

            if (!(SkillUtil.getSkillInstance(serverPlayer, SkillRegistry.PIERCING_MOMENTUM.getSkillId()) instanceof PiercingMomentumSkill skill)) {
                return;
            }

            EffectUtil.stackEffect(serverPlayer, true, getMaxStacks(), EffectRegistry.PIERCING_MOMENTUM.get(), EFFECT_DURATION, 0, false, true);
            skill.projectileList.remove(projectile);
        }
    }
}