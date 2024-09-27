package me.cat.skilled.skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ParrySkill {
    private static final float KNOCKBACK_STRENGTH = 1;
    private static final int WEAKNESS_DURATION = 60;
    private static final int WEAKNESS_AMPLIFIER = 1;
    private static final int SLOWNESS_DURATION = 60;
    private static final int SLOWNESS_AMPLIFIER = 2;

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer
                && event.getEntity().getUseItem().getItem() instanceof ShieldItem
                && event.getSource().getDirectEntity() instanceof LivingEntity source) {
            double xDir = serverPlayer.position().x - source.position().x;
            double zDir = serverPlayer.position().z - source.position().z;
            source.knockback(KNOCKBACK_STRENGTH, xDir, zDir);
            source.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, WEAKNESS_DURATION, WEAKNESS_AMPLIFIER));
            source.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SLOWNESS_DURATION, SLOWNESS_AMPLIFIER));
        }
    }
}
