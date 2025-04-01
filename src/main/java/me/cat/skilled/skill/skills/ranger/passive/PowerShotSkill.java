package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.skill.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PowerShotSkill extends Skill {
    public static final int MAX_LEVEL = 5;

    private static final double START_VELOCITY_MULTIPLIER = 1.05;
    private static final double VELOCITY_PER_LEVEL_ADDITIVE = 0.05;

    public static double getVelocityMultiplier(int level) {
        return START_VELOCITY_MULTIPLIER + level * VELOCITY_PER_LEVEL_ADDITIVE;
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) {
            return;
        }

        if (!(arrow.getOwner() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.POWER_SHOT.getSkillId()) instanceof PowerShotSkill powerShotSkill)) {
            return;
        }

        arrow.setDeltaMovement(arrow.getDeltaMovement().scale(getVelocityMultiplier(powerShotSkill.getLevel() - 1)));
        arrow.hasImpulse = true;
    }
}
