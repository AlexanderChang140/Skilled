package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ImmobilizingShotSkill extends Skill {
    public static final int MAX_LEVEL = 5;

    private static final int IMMOBILIZED_DURATION = 100;

    public ImmobilizingShotSkill() {
        super(1);
    }

    @SubscribeEvent
    public static void onLivingAttackEvent(LivingAttackEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.IMMOBILIZING_SHOT.getSkillId()) instanceof ImmobilizingShotSkill immobilizingShotSkill)) {
            return;
        }

        if (!(event.getSource().getDirectEntity() instanceof Projectile)) {
            return;
        }

        var mobEffectInstance = new MobEffectInstance(EffectRegistry.IMMOBILIZED.get(), IMMOBILIZED_DURATION, immobilizingShotSkill.level - 1, false, false);
        event.getEntity().addEffect(mobEffectInstance);
    }
}
