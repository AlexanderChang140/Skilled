package me.cat.skilled.skill.skills.paladin.passive;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

public class AuraOfProtectionSkill extends Skill {
    private static final double PROTECTION_RADIUS = 5.0;
    private static final float DAMAGE_MULTIPLIER = 0.8f;
    private static final Set<LivingEntity> LIVING_ENTITIES = new HashSet<>();

    @Mod.EventBusSubscriber
    public static class AuraOfProtectionEventHandler {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (!(event.player instanceof ServerPlayer serverPlayer)) return;
            if (!PlayerSkillManager.hasSkill(serverPlayer, SkillRegistry.AURA_OF_PROTECTION.getSkillId())) return;

            AABB area = serverPlayer.getBoundingBox().inflate(PROTECTION_RADIUS);
            LIVING_ENTITIES.addAll(serverPlayer.level().getEntitiesOfClass(LivingEntity.class, area, livingEntity -> livingEntity.getTeam() == serverPlayer.getTeam()));
        }

        @SubscribeEvent
        public static void onLivingHurtEvent(LivingHurtEvent event) {
            if (LIVING_ENTITIES.contains(event.getEntity()) && event.getSource().getEntity() instanceof LivingEntity) {
                event.setAmount(event.getAmount() * DAMAGE_MULTIPLIER);
            }
        }

        @SubscribeEvent
        public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
            if (!event.getLevel().isClientSide && event.getEntity() instanceof LivingEntity livingEntity) {
                LIVING_ENTITIES.remove(livingEntity);
            }
        }
    }
}
