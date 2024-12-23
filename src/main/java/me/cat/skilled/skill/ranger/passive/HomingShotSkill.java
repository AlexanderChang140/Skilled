package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.effect.MarkedEffect;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
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
    private static final double HOMING_DISTANCE = 10;
    private static final double HOMING_SPEED = 0.5;
    private static final HashSet<Projectile> PROJECTILE_LIST = new HashSet<>();

    public HomingShotSkill() {
        super(3);
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide &&
                event.getEntity() instanceof Projectile projectile &&
                projectile.getOwner() instanceof ServerPlayer serverPlayer &&
                SkillUtil.getSkillInstance(serverPlayer, SkillIds.HOMING_SHOT) instanceof HomingShotSkill homingShotSkill) {
            PROJECTILE_LIST.add(projectile);
        }
    }

    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide &&
                event.getEntity() instanceof Projectile projectile) {
            PROJECTILE_LIST.remove(projectile);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!event.getProjectile().level().isClientSide) {
            PROJECTILE_LIST.remove(event.getProjectile());
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        for (Projectile projectile: PROJECTILE_LIST) {
            LivingEntity target = getClosestTarget(projectile.position());
            if (target != null) {
                Vec3 newVelocity = calculateVelocity(projectile, target);
                setProjectileRotation(projectile, newVelocity);
                projectile.setDeltaMovement(newVelocity);
                projectile.hasImpulse = true;
            }
        }
    }

    private static LivingEntity getClosestTarget(Vec3 position) {
        LivingEntity target = null;
        for (LivingEntity livingEntity : MarkedEffect.MARKED_LIST) {
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
}
