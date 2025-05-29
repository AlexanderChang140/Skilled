package me.cat.skilled.skill.skills.paladin.active;

import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.MathUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class MendWoundsSkill extends ActiveSkill {
    private final double HEAL_RADIUS = 5.0;
    private final float HEAL_AMOUNT = 10.0f;

    protected MendWoundsSkill() {
        super(300);
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        AABB area = serverPlayer.getBoundingBox().inflate(HEAL_RADIUS);
        serverPlayer.level().getEntitiesOfClass(LivingEntity.class, area).forEach(livingEntity -> {
            if (livingEntity.getTeam() == serverPlayer.getTeam()) {
                livingEntity.heal(HEAL_AMOUNT);
            }
        });
        return true;
    }
}
