package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.skill.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class EvasionSkill extends Skill {
    public static final int MAX_LEVEL = 5;
    private static final double MAX_EVASION = 100.0;
    private static final int TICKS_BEFORE_START_RECHARGE = 200;
    private static final int TICKS_TO_FULL_RECHARGE = 200;

    private final TickTimer rechargeStartTimer = new TickTimer(TICKS_BEFORE_START_RECHARGE);
    private boolean isRecharging = false;
    private double currEvasion = MAX_EVASION;

    public static double getMaxEvasion(int level) {
        return MAX_EVASION;
    }

    @SubscribeEvent
    public static void onLivingEntityAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }
        if (!(PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.EVASION.getSkillId()) instanceof EvasionSkill evasionSkill)) {
            return;
        }

        evasionSkill.isRecharging = false;
        evasionSkill.rechargeStartTimer.resetTickCounter();

        Random random = new Random();
        if (random.nextDouble(100) < evasionSkill.currEvasion) {
             event.setCanceled(true);
             evasionSkill.currEvasion /= 2;
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!(event.player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.EVASION.getSkillId()) instanceof EvasionSkill evasionSkill)) {
            return;
        }

        if (evasionSkill.currEvasion >= MAX_EVASION) {
            evasionSkill.currEvasion = Math.max(evasionSkill.currEvasion, MAX_EVASION);
            return;
        }

        if (!evasionSkill.isRecharging && evasionSkill.rechargeStartTimer.doTick()) {
            evasionSkill.isRecharging = true;
            return;
        }

        double evasionPerTick = MAX_EVASION / TICKS_TO_FULL_RECHARGE;
        evasionSkill.currEvasion = Math.max(evasionSkill.currEvasion + evasionPerTick, MAX_EVASION);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putInt("recharge_start_timer", rechargeStartTimer.getTickCounter());
        tag.putBoolean("is_recharging", isRecharging);
        tag.putDouble("curr_evasion", currEvasion);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        rechargeStartTimer.setTickCounter(tag.getInt("recharge_start_timer"));
        isRecharging = tag.getBoolean("is_recharging");
        currEvasion = tag.getDouble("curr_evasion");
    }
}
