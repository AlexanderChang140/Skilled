package me.cat.skilled.skill.warrior.passive;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.EffectUtil;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FrenzySkill extends Skill {
    private static final int FRENZY_DURATION = 100;
    private static final int MAX_FRENZY_STACKS = 4;

    public FrenzySkill() {
        super(1);
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.hasSkill(serverPlayer, SkillIds.FRENZY))) {
            return;
        }

        if (!serverPlayer.hasEffect(EffectRegistry.FRENZY.get())) {
            MobEffectInstance mobEffectInstance = new MobEffectInstance(EffectRegistry.FRENZY.get(), FRENZY_DURATION, 0, false, false);
            serverPlayer.addEffect(mobEffectInstance);
            return;
        }

        if (serverPlayer.getEffect(EffectRegistry.FRENZY.get()).getAmplifier() < MAX_FRENZY_STACKS) {
            EffectUtil.incrementEffect(serverPlayer, EffectRegistry.FRENZY.get());
        }
        EffectUtil.setEffectDuration(serverPlayer, EffectRegistry.FRENZY.get(), FRENZY_DURATION);
    }
}
