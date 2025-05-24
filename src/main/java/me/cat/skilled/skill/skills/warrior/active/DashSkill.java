package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.TickTimer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.List;

public class DashSkill extends ActiveSkill {
    public static final double DASH_SPEED = 1.5;
    private static final double DASH_HIT_RADIUS = 1.5;
    private static final float DASH_HIT_DAMAGE = 5.0f;
    private static final double DASH_HIT_KNOCKBACK = 1.0;

    private final TickTimer dashDuration = new TickTimer(20);
    private final HashSet<Entity> hitSet = new HashSet<>();
    private boolean isDashing = false;

    public DashSkill() {
        super(200);
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        isDashing = true;
        Vec3 motion = serverPlayer.getDeltaMovement();
        Vec3 lookDirection = serverPlayer.getLookAngle();
        Vec3 dashVelocity = new Vec3(
                lookDirection.x * DASH_SPEED,
                lookDirection.y * DASH_SPEED,
                lookDirection.z * DASH_SPEED
        );

        serverPlayer.setDeltaMovement(motion.add(dashVelocity));
        serverPlayer.fallDistance = 0.0F;
        serverPlayer.hurtMarked = true;
        return true;
    }

    public static class DashEventHandler {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (!(event.player instanceof ServerPlayer serverPlayer)) return;
            if (!(PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.DASH.getSkillId()) instanceof DashSkill dashSkill)) return;
            if (!dashSkill.isDashing) return;
            if (dashSkill.dashDuration.doTick()) {
                dashSkill.dashDuration.resetTickCounter();
                dashSkill.hitSet.clear();
                dashSkill.isDashing = false;
                return;
            }

            AABB area = serverPlayer.getBoundingBox().inflate(DASH_HIT_RADIUS);
            List<LivingEntity> nearbyEntities = serverPlayer.level().getEntitiesOfClass(LivingEntity.class, area, livingEntity -> !dashSkill.hitSet.contains(livingEntity));
            dashSkill.hitSet.addAll(nearbyEntities);

            nearbyEntities.forEach(livingEntity -> {
                double xDir = serverPlayer.position().x - livingEntity.position().x;
                double zDir = serverPlayer.position().z - livingEntity.position().z;
                if (Math.hypot(xDir, zDir) <= DASH_HIT_RADIUS) {
                    livingEntity.knockback(DASH_HIT_KNOCKBACK, xDir, zDir);
                    livingEntity.hurt(livingEntity.damageSources().playerAttack(serverPlayer), DASH_HIT_DAMAGE);
                }
            });
        }

        @SubscribeEvent
        public static void onLivingAttack(LivingAttackEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
            if (!(PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.DASH.getSkillId()) instanceof DashSkill dashSkill)) return;
            if (dashSkill.isDashing) {
                event.setCanceled(true);
            }
        }
    }
}
