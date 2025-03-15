package me.cat.skilled.experience;

import me.cat.skilled.capability.manager.PlayerLevelManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExperienceSystem {
    private static final Map<LivingEntity, Map<ServerPlayer, Float>> experienceMap = new HashMap<>();

    private static boolean isValidSource(LivingEntity livingEntity) {
        return livingEntity instanceof Monster
                || livingEntity instanceof NeutralMob
                || (livingEntity.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) && livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) > 0);
    }

    @Mod.EventBusSubscriber
    public static class ExperienceEvents {
        @SubscribeEvent
        public static void onLivingDamaged(LivingDamageEvent event) {
            if (!(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)) {
                return;
            }

            LivingEntity livingEntity = event.getEntity();
            if (!isValidSource(livingEntity)) {
                return;
            }

            var playerMap = experienceMap.computeIfAbsent(livingEntity, (key) -> new HashMap<>());
            playerMap.put(serverPlayer, playerMap.getOrDefault(serverPlayer, 0.0f) + Math.min(event.getAmount(), livingEntity.getHealth()));
        }

        @SubscribeEvent
        public static void onLivingEntityDeath(LivingDeathEvent event) {
            if (event.getEntity().level().isClientSide) {
                return;
            }
            Optional.ofNullable(experienceMap.get(event.getEntity())).ifPresent(map -> {
                float maxHealth = event.getEntity().getMaxHealth();
                for (var entry: map.entrySet()) {
                    float damageDone = entry.getValue();
                    if (damageDone == 0) {
                        continue;
                    }
                    int baseExperience = event.getEntity().getExperienceReward();
                    int experienceGained = (int) (baseExperience * Math.min(1, damageDone / maxHealth));
                    ServerPlayer serverPlayer = entry.getKey();
                    PlayerLevelManager.addPlayerExperience(serverPlayer, experienceGained);
                }
            });
        }

        @SubscribeEvent
        public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
            if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof LivingEntity livingEntity) {
                experienceMap.remove(livingEntity);
            }
        }
    }
}
