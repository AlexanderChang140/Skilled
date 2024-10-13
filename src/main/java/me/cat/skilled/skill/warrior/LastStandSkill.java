package me.cat.skilled.skill.warrior;

import me.cat.skilled.capability.SerializedSkill;
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
public class LastStandSkill extends Skill implements SerializedSkill {

    private final TickTimer skillCooldown = new TickTimer(12000);
    private final TickTimer invulnTimer = new TickTimer(100);
    private boolean isSkillReady = false;
    private boolean isInvuln = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.LAST_STAND) instanceof LastStandSkill lastStandSkill) {
            if (!lastStandSkill.isSkillReady && lastStandSkill.skillCooldown.doTick()) {
                lastStandSkill.isSkillReady = true;
            }

            if (lastStandSkill.isInvuln && lastStandSkill.skillCooldown.doTick()) {
                lastStandSkill.isInvuln = false;
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.LAST_STAND) instanceof LastStandSkill lastStandSkill) {
            LivingEntity livingEntity = event.getEntity();
            float damage = event.getAmount();

            if (lastStandSkill.isSkillReady && damage >= livingEntity.getHealth()) {
                event.setCanceled(true);

                lastStandSkill.isSkillReady = false;
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
        CompoundTag tag = new CompoundTag();
        tag.putInt("skill_cooldown", skillCooldown.getTickCounter());
        tag.putInt("invuln_timer", invulnTimer.getTickCounter());
        tag.putBoolean("is_skill_ready", isSkillReady);
        tag.putBoolean("is_invuln", isInvuln);
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        skillCooldown.setTickCounter(tag.getInt("skill_cooldown"));
        invulnTimer.setTickCounter(tag.getInt("invuln_timer"));
        isSkillReady = tag.getBoolean("is_skill_ready");
        isInvuln = tag.getBoolean("is_invuln");
    }
}
