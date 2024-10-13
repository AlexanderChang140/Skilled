package me.cat.skilled.skill.warrior;

import me.cat.skilled.capability.SerializedSkill;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ParrySkill extends Skill implements SerializedSkill {
    private static final double KNOCKBACK_STRENGTH = 1;
    private static final int WEAKNESS_DURATION = 60;
    private static final int WEAKNESS_AMPLIFIER = 1;
    private static final int SLOWNESS_DURATION = 60;
    private static final int SLOWNESS_AMPLIFIER = 2;

    private final TickTimer parryCooldown = new TickTimer(100);
    private boolean isParryReady = true;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.PARRY) instanceof ParrySkill parrySkill) {
            if (!parrySkill.isParryReady && parrySkill.parryCooldown.doTick()) {
                parrySkill.isParryReady = true;
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer && SkillUtil.getSkillInstance(serverPlayer, SkillIds.PARRY) instanceof ParrySkill parrySkill) {
            if (parrySkill.isParryReady && event.getEntity().getUseItem().getItem() instanceof ShieldItem && event.getSource().getDirectEntity() instanceof LivingEntity source) {
                double xDir = serverPlayer.position().x - source.position().x;
                double zDir = serverPlayer.position().z - source.position().z;
                source.knockback(KNOCKBACK_STRENGTH, xDir, zDir);
                source.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, WEAKNESS_DURATION, WEAKNESS_AMPLIFIER));
                source.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SLOWNESS_DURATION, SLOWNESS_AMPLIFIER));
                parrySkill.isParryReady = false;
            }
        }
    }

    @Override
    public CompoundTag saveNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("parry_cooldown", parryCooldown.getTickCounter());
        tag.putBoolean("is_parry_ready", isParryReady);
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        parryCooldown.setTickCounter(tag.getInt("parry_cooldown"));
        isParryReady = tag.getBoolean("is_parry_ready");
    }
}
