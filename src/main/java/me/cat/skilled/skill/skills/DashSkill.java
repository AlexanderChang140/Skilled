package me.cat.skilled.skill.skills;

import me.cat.skilled.skill.ActiveSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class DashSkill extends ActiveSkill {
    private static final double DASH_SPEED = 1.5;

    public DashSkill() {
        super(200);
    }

    @Override
    protected void onActivateSkill(ServerPlayer serverPlayer) {
        Vec3 motion = serverPlayer.getDeltaMovement();
        Vec3 lookDirection = serverPlayer.getLookAngle();
        Vec3 dashVelocity = new Vec3(
                lookDirection.x * DASH_SPEED,
                lookDirection.y * DASH_SPEED,
                lookDirection.z * DASH_SPEED
        );

        serverPlayer.setDeltaMovement(motion.add(dashVelocity));
        serverPlayer.fallDistance = 0.0F;
    }
}
