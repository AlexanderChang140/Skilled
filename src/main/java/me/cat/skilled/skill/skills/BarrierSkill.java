package me.cat.skilled.skill.skills;

import me.cat.skilled.registry.AttributeRegistry;
import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.EffectUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;

import java.util.Objects;


public class BarrierSkill extends Skill {

    private final TickTimer timer = new TickTimer(200);

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!timer.doTick()) {
            return;
        }

        var attributeInstance = event.player.getAttribute(AttributeRegistry.BARRIER_LEVEL.get());
        int barrierLevel = attributeInstance != null ? (int) attributeInstance.getValue() : 0;

        boolean hasEffect = event.player.hasEffect(EffectRegistry.BARRIER.get());
        int currentAmplifier = hasEffect ? Objects.requireNonNull(event.player.getEffect(EffectRegistry.BARRIER.get())).getAmplifier() : -1;

        if (!(currentAmplifier + 1 < barrierLevel) || !timer.doTick()) {
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
