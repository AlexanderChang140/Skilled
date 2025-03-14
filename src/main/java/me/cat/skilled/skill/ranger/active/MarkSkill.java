package me.cat.skilled.skill.ranger.active;

import com.google.common.collect.HashMultimap;
import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.ActiveSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class MarkSkill extends ActiveSkill {
    private static final double MARK_DISTANCE = 100.0;
    private static final int MARK_DURATION = 200;

    public static final HashMultimap<LivingEntity, MarkSkill> MARKED_TO_PLAYER_MAP = HashMultimap.create();

    public final HashSet<LivingEntity> markedSet = new HashSet<>();

    public MarkSkill() {
        super(60, 1);
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        LivingEntity livingEntity = playerToLivingEntityRaycast(serverPlayer, MARK_DISTANCE);
        if (livingEntity != null) {
            MobEffectInstance mobEffectInstance = new MobEffectInstance(EffectRegistry.MARKED.get(), MARK_DURATION, 1, false, false);
            livingEntity.addEffect(mobEffectInstance);
            markedSet.add(livingEntity);
            MARKED_TO_PLAYER_MAP.put(livingEntity, this);
            return true;
        }
        return false;
    }

    public static LivingEntity playerToLivingEntityRaycast(Player player, double maxDistance) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 lookVector = player.getViewVector(1.0F);
        Vec3 endPosition = eyePosition.add(lookVector.scale(maxDistance));

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                eyePosition,
                endPosition,
                new AABB(eyePosition, endPosition).inflate(1.0),
                entity -> entity != player
        );

        return entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    public Collection<LivingEntity> getMarkedEntities() {
        return Collections.unmodifiableCollection(markedSet);
    }

    public static void removeMarkedEntity(LivingEntity livingEntity) {
        for (MarkSkill skill : MARKED_TO_PLAYER_MAP.get(livingEntity)) {
            skill.markedSet.remove(livingEntity);
        }
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
            if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof LivingEntity livingEntity) {
                removeMarkedEntity(livingEntity);
            }
        }
    }
}
