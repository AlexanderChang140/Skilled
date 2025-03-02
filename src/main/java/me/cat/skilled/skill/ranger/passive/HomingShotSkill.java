package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.skill.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.ranger.active.MarkSkill;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;

@Mod.EventBusSubscriber
public class HomingShotSkill extends Skill {
    public static final int MAX_LEVEL = 1;

    private static final double HOMING_DISTANCE = 10;
    private static final double HOMING_SPEED = 0.5;
    private static final HashSet<Projectile> PROJECTILE_SET = new HashSet<>();

    public HomingShotSkill() {
        super(1);
    }

    private static LivingEntity getClosestTarget(ServerPlayer serverPlayer, Vec3 position) {
        if (!((PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.MARK.getSkillId())) instanceof MarkSkill skill)) {
            return null;
        }

        LivingEntity target = null;
        for (LivingEntity livingEntity : skill.getMarkedEntities()) {
            double distance = (livingEntity.position().subtract(position)).length();
            if (distance <= HomingShotSkill.HOMING_DISTANCE) {
                target = livingEntity;
            }
        }
        return target;
    }

    private static void setProjectileRotation(Projectile projectile, Vec3 velocity) {
        float yaw = (float) (Math.toDegrees(Math.atan2(velocity.z, velocity.x)) - 90);
        float pitch = (float) (Math.toDegrees(-Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z))));
        float alpha = 0.1f;
        projectile.setYRot(Mth.lerp(alpha, (float) projectile.getY(), yaw));
        projectile.setXRot(Mth.lerp(alpha, (float) projectile.getX(), pitch));
    }

    private static Vec3 calculateVelocity(Projectile projectile, LivingEntity target) {
        Vec3 currentPosition = projectile.position();
        Vec3 targetPosition = target.position().add(0, target.getBbHeight() / 2, 0);
        Vec3 currentDirection = projectile.getDeltaMovement().normalize();
        Vec3 targetDirection = targetPosition.subtract(currentPosition).normalize();

        double maxAngle = Math.acos(Mth.clamp(currentDirection.dot(targetDirection), -1.0, 1.0));
        double deltaAngle = Mth.clamp(HomingShotSkill.HOMING_SPEED, -maxAngle, maxAngle);
        Vec3 newDirection = currentDirection.scale(1 - deltaAngle).add(targetDirection.scale(deltaAngle)).normalize();

        double speed = projectile.getDeltaMovement().length();

        return newDirection.scale(speed);
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event) {
            for (Projectile projectile : PROJECTILE_SET) {
                ServerPlayer serverPlayer = (ServerPlayer) projectile.getOwner();
                LivingEntity target = getClosestTarget(serverPlayer, projectile.position());
                if (target == null) {
                    continue;
                }

                Vec3 newVelocity = calculateVelocity(projectile, target);
                //setProjectileRotation(projectile, newVelocity); // Not working as intended
                projectile.setDeltaMovement(newVelocity);
                projectile.hasImpulse = true;
            }
        }

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            if (event.getLevel().isClientSide()) {
                return;
            }

            if (!(event.getEntity() instanceof Projectile projectile)) {
                return;
            }

            if (!(projectile.getOwner() instanceof ServerPlayer serverPlayer)) {
                return;
            }

            if (!(PlayerSkillManager.hasSkill(serverPlayer, SkillRegistry.HOMING_SHOT.getSkillId()))) {
                return;
            }

            PROJECTILE_SET.add(projectile);
        }

        @SubscribeEvent
        public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
            if (event.getLevel().isClientSide) {
                return;
            }

            if (!(event.getEntity() instanceof Projectile projectile)) {
                return;
            }

            PROJECTILE_SET.remove(projectile);
        }

        @SubscribeEvent
        public static void onProjectileImpact(ProjectileImpactEvent event) {
            Projectile projectile = event.getProjectile();

            if (projectile.level().isClientSide) {
                return;
            }

            PROJECTILE_SET.remove(event.getProjectile());
        }
    }
}
