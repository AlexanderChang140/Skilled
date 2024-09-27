package me.cat.skilled.skill;

import me.cat.skilled.registry.AttributeRegistry;
import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.util.EffectUtil;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;


@Mod.EventBusSubscriber
public class BarrierSkill {

    private static final TickTimer timer = new TickTimer(200);

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide) {
            return;
        }

        boolean hasSkill = SkillUtil.hasSkill(event.player, SkillIds.BARRIER_SKILL);
        var attributeInstance = event.player.getAttribute(AttributeRegistry.BARRIER_LEVEL.get());
        int barrierLevel = attributeInstance != null ? (int) attributeInstance.getValue() : 0;

        boolean hasEffect = event.player.hasEffect(EffectRegistry.BARRIER.get());
        int currentAmplifier = hasEffect ? Objects.requireNonNull(event.player.getEffect(EffectRegistry.BARRIER.get())).getAmplifier() : -1;

        if (!hasSkill
                || !(currentAmplifier + 1 < barrierLevel)
                || !timer.doTick()) {
            return;
        }

        if (hasEffect) {
            EffectUtil.incrementEffect(event.player, EffectRegistry.BARRIER.get());
        }
        else {
            event.player.addEffect(new MobEffectInstance(EffectRegistry.BARRIER.get(), -1, 0, false, false));
        }
    }
}
