package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ShrapnelBarrageSkill extends Skill {
    public static final double RADIUS = 5.0;
    public static final double BASE_DAMAGE_MULTIPLIER = 0.5;
    public static final double PER_LEVEL_ADDITIVE_DAMAGE_MULTIPLIER = 0.1;

    public ShrapnelBarrageSkill() {
        super(1);
    }

    public static double getDamageMultiplier(int level) {
        return BASE_DAMAGE_MULTIPLIER + (level - 1) * PER_LEVEL_ADDITIVE_DAMAGE_MULTIPLIER;
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onLivingAttack(LivingAttackEvent event) {
            if (!event.getEntity().hasEffect(EffectRegistry.MARKED.get())) {
                return;
            }

            if (!(event.getSource().getDirectEntity() instanceof Projectile projectile)) {
                return;
            }

            if (!(projectile.getOwner() instanceof ServerPlayer serverPlayer)) {
                return;
            }

            if (!(PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.SHRAPNEL_BARRAGE.getSkillId()) instanceof ShrapnelBarrageSkill skill)) {
                return;
            }

            LivingEntity target = event.getEntity();
            AABB area = new AABB(
                    target.getX() - RADIUS,
                    target.getY() - RADIUS,
                    target.getZ() - RADIUS,
                    target.getX() + RADIUS,
                    target.getY() + RADIUS,
                    target.getZ() + RADIUS
            );

            List<Entity> nearbyEntities = serverPlayer.level().getEntities(serverPlayer, area, entity -> entity instanceof LivingEntity && entity != target);
            for (Entity entity : nearbyEntities) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (!target.position().closerThan(livingEntity.position(), RADIUS)) {
                    continue;
                }

                float damage = (float) (event.getAmount() * getDamageMultiplier(skill.getLevel()));
                livingEntity.hurt(livingEntity.damageSources().playerAttack(serverPlayer), damage);
            }
        }
    }
}
