package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StealthSkill extends Skill {
    private final TickTimer stealthCooldown = new TickTimer(200);

    public StealthSkill() {
        super(1);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.STEALTH) instanceof StealthSkill stealthSkill) {
            if (!serverPlayer.hasEffect(EffectRegistry.STEALTH.get()) && stealthSkill.stealthCooldown.doTick()) {
                serverPlayer.addEffect(new MobEffectInstance(EffectRegistry.STEALTH.get(), -1, 0, false, false));
            }
        }
    }
}
