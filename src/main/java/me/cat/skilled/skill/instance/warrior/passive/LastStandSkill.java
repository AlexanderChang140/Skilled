package me.cat.skilled.skill.instance.warrior.passive;

import me.cat.skilled.skill.instance.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer ;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LastStandSkill extends Skill {
    public static final int MAX_LEVEL = 1;
    private final TickTimer lastStandCooldown = new TickTimer(12000);
    private boolean isLastStandReady = true;
    private final TickTimer invulnTimer = new TickTimer(100);
    private boolean isInvuln = false;

    public LastStandSkill() {
        super();
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!(event.player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.getSkillInstance(serverPlayer, SkillRegistry.LAST_STAND.getSkillId()) instanceof LastStandSkill lastStandSkill)) {
            return;
        }

        if (!lastStandSkill.isLastStandReady && lastStandSkill.lastStandCooldown.doTick()) {
            lastStandSkill.isLastStandReady = true;
        }

        if (lastStandSkill.isInvuln && lastStandSkill.invulnTimer.doTick()) {
            lastStandSkill.isInvuln = false;
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.getSkillInstance(serverPlayer, SkillRegistry.LAST_STAND.getSkillId()) instanceof LastStandSkill lastStandSkill)) {
            return;
        }

        if (lastStandSkill.isInvuln) {
            event.setCanceled(true);
            return;
        }

        LivingEntity livingEntity = event.getEntity();
        float damage = event.getAmount();

        if (lastStandSkill.isLastStandReady && damage >= livingEntity.getHealth()) {
            event.setCanceled(true);

            lastStandSkill.isLastStandReady = false;
            lastStandSkill.isInvuln = true;

            livingEntity.getCombatTracker().recordDamage(event.getSource(), damage);
            livingEntity.setHealth(1);
            livingEntity.setAbsorptionAmount(0);
            livingEntity.gameEvent(GameEvent.ENTITY_DAMAGE);
        }
    }

    @Override
    public CompoundTag saveNbt() {
        CompoundTag tag = super.saveNbt();
        tag.putInt("skill_cooldown", lastStandCooldown.getTickCounter());
        tag.putInt("invuln_timer", invulnTimer.getTickCounter());
        tag.putBoolean("is_skill_ready", isLastStandReady);
        tag.putBoolean("is_invuln", isInvuln);
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        super.loadNbt(tag);
        lastStandCooldown.setTickCounter(tag.getInt("skill_cooldown"));
        invulnTimer.setTickCounter(tag.getInt("invuln_timer"));
        isLastStandReady = tag.getBoolean("is_skill_ready");
        isInvuln = tag.getBoolean("is_invuln");
    }
}
