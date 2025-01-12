package me.cat.skilled.skill.instance.warrior.active;

import me.cat.skilled.skill.instance.ActiveSkill;
import me.cat.skilled.util.GameUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SlashSkill extends ActiveSkill {
    public static final int MAX_LEVEL = 1;

    public static final double RADIUS = 3.5;
    public static final double ANGLE = 90;
    public static final float DAMAGE_MULTIPLIER = 10.0f;
    public static final double KNOCKBACK = 0.5;

    public SlashSkill() {
        super(100);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        AABB area = new AABB(
                serverPlayer.getX() - RADIUS,
                serverPlayer.getY() - RADIUS,
                serverPlayer.getZ() - RADIUS,
                serverPlayer.getX() + RADIUS,
                serverPlayer.getY() + RADIUS,
                serverPlayer.getZ() + RADIUS
        );

        List<Entity> nearbyEntities = serverPlayer.level().getEntities(serverPlayer, area, entity -> entity instanceof LivingEntity);

        for (Entity entity : nearbyEntities) {
            LivingEntity livingEntity = (LivingEntity) entity;

            if (!serverPlayer.position().closerThan(livingEntity.position(), RADIUS)) {
                continue;
            }

            if (GameUtil.calculateAngleBetween(serverPlayer.getLookAngle(), livingEntity.position().subtract(serverPlayer.position())) > ANGLE) {
                continue;
            }

            float damage = (float) (serverPlayer.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * DAMAGE_MULTIPLIER);
            livingEntity.hurt(livingEntity.damageSources().playerAttack(serverPlayer), damage);

            double xDir = serverPlayer.position().x - livingEntity.position().x;
            double zDir = serverPlayer.position().z - livingEntity.position().z;
            livingEntity.knockback(KNOCKBACK, xDir, zDir);
        }
        return true;
    }
}
