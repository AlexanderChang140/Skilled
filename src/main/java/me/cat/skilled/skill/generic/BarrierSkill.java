package me.cat.skilled.skill.generic;

import me.cat.skilled.capability.SerializedSkill;
import me.cat.skilled.registry.AttributeRegistry;
import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.EffectUtil;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber
public class BarrierSkill extends Skill implements SerializedSkill {
    private final TickTimer barrierCooldown = new TickTimer(200);

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.BARRIER) instanceof BarrierSkill barrierSkill) {
            var attributeInstance = event.player.getAttribute(AttributeRegistry.BARRIER_LEVEL.get());
            int barrierLevel = attributeInstance != null ? (int) attributeInstance.getValue() : 0;

            boolean hasEffect = event.player.hasEffect(EffectRegistry.BARRIER.get());
            int currentAmplifier = hasEffect ? Objects.requireNonNull(event.player.getEffect(EffectRegistry.BARRIER.get())).getAmplifier() : -1;

            if (!(currentAmplifier + 1 < barrierLevel) || !barrierSkill.barrierCooldown.doTick()) {
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

    @Override
    public CompoundTag saveNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("barrier_cooldown", barrierCooldown.getTickCounter());
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        barrierCooldown.setTickCounter(tag.getInt("barrier_cooldown"));
    }
}