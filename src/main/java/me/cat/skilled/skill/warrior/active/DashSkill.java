package me.cat.skilled.skill.warrior.active;

import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.DashS2CPacket;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;

@Mod.EventBusSubscriber
public class DashSkill extends ActiveSkill {
    public static final double DASH_SPEED = 1.5;
    private static final double DASH_HIT_RADIUS = 1;
    private static final float DASH_HIT_DAMAGE = 5;
    private static final double DASH_HIT_KNOCKBACK = 1;

    private final TickTimer dashDuration = new TickTimer(20);
    private final HashSet<Entity> hitSet = new HashSet<>();
    private boolean isDashing = false;

    public DashSkill() {
        super(1, 200);
    }

    @Override
    protected void onActivateSkill(ServerPlayer serverPlayer) {
        isDashing = true;
        Messenger.sendToPlayer(new DashS2CPacket(), serverPlayer);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!(event.player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.getSkillInstance(serverPlayer, SkillIds.DASH) instanceof DashSkill dashSkill)) {
            return;
        }

        if (!dashSkill.isDashing) {
            return;
        }

        if (dashSkill.dashDuration.doTick()) {
            dashSkill.dashDuration.resetTickCounter();
            dashSkill.hitSet.clear();
            dashSkill.isDashing = false;
            return;
        }

        AABB area = new AABB(
                serverPlayer.getX() - DASH_HIT_RADIUS,
                serverPlayer.getY() - DASH_HIT_RADIUS,
                serverPlayer.getZ() - DASH_HIT_RADIUS,
                serverPlayer.getX() + DASH_HIT_RADIUS,
                serverPlayer.getY() + DASH_HIT_RADIUS,
                serverPlayer.getZ() + DASH_HIT_RADIUS
        );

        List<Entity> nearbyEntities = event.player.level().getEntities(serverPlayer, area, entity -> entity instanceof LivingEntity && !dashSkill.hitSet.contains(entity));
        dashSkill.hitSet.addAll(nearbyEntities);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity livingEntity) {
                double xDir = serverPlayer.position().x - livingEntity.position().x;
                double zDir = serverPlayer.position().z - livingEntity.position().z;
                livingEntity.knockback(DASH_HIT_KNOCKBACK, xDir, zDir);
                livingEntity.hurt(livingEntity.damageSources().generic(), DASH_HIT_DAMAGE);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.getSkillInstance(serverPlayer, SkillIds.DASH) instanceof DashSkill dashSkill)) {
            return;
        }

        if (dashSkill.isDashing) {
            event.setCanceled(true);
        }
    }
}
