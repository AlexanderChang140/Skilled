package me.cat.skilled.skill.ranger.active;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.ActiveSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class MarkSkill extends ActiveSkill {

    private static final double MARK_DISTANCE = 100.0;
    private static final int MARK_DURATION = 100;

    public MarkSkill() {
        super(3, 60);
    }

    @Override
    protected void onActivateSkill(ServerPlayer serverPlayer) {
        LivingEntity livingEntity = playerToLivingEntityRaycast(serverPlayer, MARK_DISTANCE);
        if (livingEntity != null) {
            MobEffectInstance mobEffectInstance = new MobEffectInstance(EffectRegistry.MARKED.get(), MARK_DURATION, 1, false, false);
            livingEntity.addEffect(mobEffectInstance);
        }
    }

    public static LivingEntity playerToLivingEntityRaycast(Player player, double maxDistance) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 lookVector = player.getViewVector(1.0F);
        Vec3 endPosition = eyePosition.add(lookVector.scale(maxDistance));

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                eyePosition,
                endPosition,
                new AABB(eyePosition, endPosition).inflate(1.0),
                entity -> entity != player
        );

        return entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity ? livingEntity : null;
    }
}
