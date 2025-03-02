package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.TickTimer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StealthSkill extends Skill {
    public static final int MAX_LEVEL = 5;

    private final TickTimer stealthCooldown = new TickTimer(200);

    public StealthSkill() {
        super(1);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!(event.player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.STEALTH.getSkillId()) instanceof StealthSkill stealthSkill)) {
            return;
        }

        if (!serverPlayer.hasEffect(EffectRegistry.STEALTH.get()) && stealthSkill.stealthCooldown.doTick()) {
            MobEffectInstance mobEffectInstance = new MobEffectInstance(EffectRegistry.STEALTH.get(), -1, 0, false, false);
            serverPlayer.addEffect(mobEffectInstance);
        }
    }
}
