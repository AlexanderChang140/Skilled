package me.cat.skilled.skill;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.util.EffectUtil;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FrenzySkill {
    public static final int FRENZY_DURATION = 100;

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer && SkillUtil.hasSkill(serverPlayer, SkillIds.FRENZY)) {
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
}
