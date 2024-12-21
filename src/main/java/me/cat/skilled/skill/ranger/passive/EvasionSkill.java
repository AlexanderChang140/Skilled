package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class EvasionSkill extends Skill{
    private static final double MAX_EVASION = 80;
    private static final int TICKS_BEFORE_START_RECHARGE = 200;
    private static final int TICKS_TO_FULL_RECHARGE = 200;

    private final TickTimer rechargeStartTimer = new TickTimer(TICKS_BEFORE_START_RECHARGE);
    private boolean isRecharging = false;
    private double currEvasion = MAX_EVASION;

    public EvasionSkill() {
        super(3);
    }

    @SubscribeEvent
    public static void onLivingEntityAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.EVASION) instanceof EvasionSkill evasionSkill) {
            Random random = new Random();
            if (random.nextDouble(100) < evasionSkill.currEvasion) {
                 event.setCanceled(true);
                 evasionSkill.currEvasion /= 2;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.EVASION) instanceof EvasionSkill evasionSkill) {
            if (evasionSkill.currEvasion == MAX_EVASION) {
                return;
            }
            else if (evasionSkill.currEvasion > MAX_EVASION) {
                evasionSkill.currEvasion = MAX_EVASION;
                return;
            }

            if (!evasionSkill.isRecharging || !evasionSkill.rechargeStartTimer.doTick()) {
                evasionSkill.isRecharging = true;
            }
            else {
                double recharge = MAX_EVASION / TICKS_TO_FULL_RECHARGE;
                evasionSkill.currEvasion = Mth.clamp(evasionSkill.currEvasion + recharge, 0, 80);
            }
        }
    }



    @Override
    public CompoundTag saveNbt() {
        CompoundTag tag = super.saveNbt();
        tag.putInt("recharge_start_timer", rechargeStartTimer.getTickCounter());
        tag.putBoolean("is_recharging", isRecharging);
        tag.putDouble("curr_evasion", currEvasion);
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        super.loadNbt(tag);
        rechargeStartTimer.setTickCounter(tag.getInt("recharge_start_timer"));
        isRecharging = tag.getBoolean("is_recharging");
        currEvasion = tag.getDouble("curr_evasion");
    }
}
