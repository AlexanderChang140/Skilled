package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PowerShotSkill extends Skill {
    private static final double START_VELOCITY_MULTIPLIER = 1.10;
    private static final double VELOCITY_PER_LEVEL_ADDITIVE = 0.10;
    private static final double START_DAMAGE_MULTIPLIER = 1.15;
    private static final double DAMAGE_PER_LEVEL_ADDITIVE = 0.15;

    public PowerShotSkill() {
        super(1);
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) {
            return;
        }

        if (!(arrow.getOwner() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.getSkillInstance(serverPlayer, SkillIds.POWER_SHOT) instanceof PowerShotSkill powerShotSkill)) {
            return;
        }

        double velocityMultiplier = START_VELOCITY_MULTIPLIER + VELOCITY_PER_LEVEL_ADDITIVE * (powerShotSkill.getLevel() - 1);
        double damageMultiplier = START_DAMAGE_MULTIPLIER + DAMAGE_PER_LEVEL_ADDITIVE * (powerShotSkill.getLevel() - 1);
        arrow.setDeltaMovement(arrow.getDeltaMovement().scale(velocityMultiplier));
        arrow.hasImpulse = true;
        arrow.setBaseDamage(arrow.getBaseDamage() * damageMultiplier);
    }
}
