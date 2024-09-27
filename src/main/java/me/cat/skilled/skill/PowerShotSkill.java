package me.cat.skilled.skill;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PowerShotSkill {
    private static final TickTimer timer = new TickTimer(200);

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide) {
            return;
        }

        boolean hasSkill = SkillUtil.hasSkill(event.player, SkillIds.POWER_SHOT_SKILL);
        boolean hasEffect = event.player.hasEffect(EffectRegistry.POWER_SHOT.get());

        if (!hasSkill
                || hasEffect
                || !timer.doTick()) {
            return;
        }

        event.player.addEffect(new MobEffectInstance(EffectRegistry.POWER_SHOT.get(), -1, 0, false, false));
    }
}
