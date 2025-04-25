package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.MathUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;

import java.util.Objects;

public class SlashSkill extends ActiveSkill {
    public static final double RADIUS = 3.5;
    public static final double ANGLE = 90;
    public static final float DAMAGE_MULTIPLIER = 1.25f;
    public static final double KNOCKBACK = 0.5;

    public SlashSkill() {
        super(100);
    }

    public static float getDamageMultiplier() {
        return DAMAGE_MULTIPLIER;
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        AABB area = serverPlayer.getBoundingBox().inflate(RADIUS);

        serverPlayer.level().getEntitiesOfClass(LivingEntity.class, area).forEach(livingEntity ->  {
            if (!serverPlayer.position().closerThan(livingEntity.position(), RADIUS)) return;
            if (MathUtil.calculateAngleBetween(serverPlayer.getLookAngle(), livingEntity.position().subtract(serverPlayer.position())) > ANGLE) return;

            float damage = (float) (Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ATTACK_DAMAGE)).getValue() * DAMAGE_MULTIPLIER);
            livingEntity.hurt(livingEntity.damageSources().playerAttack(serverPlayer), damage);

            double xDir = serverPlayer.position().x - livingEntity.position().x;
            double zDir = serverPlayer.position().z - livingEntity.position().z;
            livingEntity.knockback(KNOCKBACK, xDir, zDir);
        });
        return true;
    }
}
