package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RepositionSkill extends Skill {
    private static final int DURATION = 60;

    @Mod.EventBusSubscriber
    public static class RepositionHandler {
        @SubscribeEvent
        public static void onArrowLoose(ArrowLooseEvent event) {
            if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
            if (!(PlayerSkillManager.hasSkill(serverPlayer, SkillRegistry.REPOSITION.getSkillId()))) return;
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, DURATION, 0, false, false);
            serverPlayer.addEffect(mobEffectInstance);
        }
    }
}
