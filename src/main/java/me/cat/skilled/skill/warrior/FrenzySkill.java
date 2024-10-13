package me.cat.skilled.skill.warrior;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.EffectUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class FrenzySkill extends Skill {
    private static final int FRENZY_DURATION = 100;

    public void onAttackEntity(AttackEntityEvent event) {
        ServerPlayer serverPlayer = (ServerPlayer) event.getEntity();
        if (serverPlayer.hasEffect(EffectRegistry.FRENZY.get())) {
            if (serverPlayer.getEffect(EffectRegistry.FRENZY.get()).getAmplifier() < 4) {
                EffectUtil.incrementEffect(serverPlayer, EffectRegistry.FRENZY.get());
            }
            EffectUtil.setEffectDuration(serverPlayer, EffectRegistry.FRENZY.get(), FRENZY_DURATION);
        }
        else {
            var mobEffectInstance = new MobEffectInstance(EffectRegistry.FRENZY.get(), FRENZY_DURATION, 0, false, false);
            serverPlayer.addEffect(mobEffectInstance);
        }
    }
}
