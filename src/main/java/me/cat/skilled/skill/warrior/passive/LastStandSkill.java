package me.cat.skilled.skill.warrior.passive;

import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.SkillIds;
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
    private final TickTimer lastStandCooldown = new TickTimer(12000);
    private boolean isLastStandReady = true;
    private final TickTimer invulnTimer = new TickTimer(100);
    private boolean isInvuln = false;

    public LastStandSkill() {
        super(1);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.LAST_STAND) instanceof LastStandSkill lastStandSkill) {
            if (!lastStandSkill.isLastStandReady && lastStandSkill.lastStandCooldown.doTick()) {
                lastStandSkill.isLastStandReady = true;
            }

            if (lastStandSkill.isInvuln && lastStandSkill.invulnTimer.doTick()) {
                lastStandSkill.isInvuln = false;
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.LAST_STAND) instanceof LastStandSkill lastStandSkill) {
            LivingEntity livingEntity = event.getEntity();
            float damage = event.getAmount();

            if (lastStandSkill.isLastStandReady && damage >= livingEntity.getHealth()) {
                event.setCanceled(true);

                lastStandSkill.isLastStandReady = false;
                lastStandSkill.isInvuln = false;

                livingEntity.getCombatTracker().recordDamage(event.getSource(), damage);
                livingEntity.setHealth(1);
                livingEntity.setAbsorptionAmount(0);
                livingEntity.gameEvent(GameEvent.ENTITY_DAMAGE);
            }
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
