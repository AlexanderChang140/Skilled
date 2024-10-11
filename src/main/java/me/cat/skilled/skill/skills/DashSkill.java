package me.cat.skilled.skill.skills;

import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.DashS2CPacket;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.TickTimer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.ArrayList;
import java.util.List;

public class DashSkill extends ActiveSkill {
    public static final double DASH_SPEED = 1.5;
    private static final double DASH_HIT_RADIUS = 1;
    private static final float DASH_HIT_DAMAGE = 5;
    private static final double DASH_HIT_KNOCKBACK = 1;

    private final TickTimer dashDuration = new TickTimer(20);
    private final List<Entity> hitList = new ArrayList<>();
    private boolean isDashing = false;

    public DashSkill() {
        super(200);
    }

    @Override
    protected void onActivateSkill(ServerPlayer serverPlayer) {
        isDashing = true;
        Messenger.sendToPlayer(new DashS2CPacket(), serverPlayer);
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!isDashing) {
            return;
        }

        Player player = event.player;

        if (dashDuration.doTick()) {
            dashDuration.setTickCounter(0);
            hitList.clear();
            isDashing = false;
            return;
        }

        AABB area = new AABB(
                player.getX() - DASH_HIT_RADIUS,
                player.getY() - DASH_HIT_RADIUS,
                player.getZ() - DASH_HIT_RADIUS,
                player.getX() + DASH_HIT_RADIUS,
                player.getY() + DASH_HIT_RADIUS,
                player.getZ() + DASH_HIT_RADIUS
        );

        List<Entity> nearbyEntities = event.player.level().getEntities(player, area, entity -> entity instanceof LivingEntity && !hitList.contains(entity));
        hitList.addAll(nearbyEntities);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity livingEntity) {
                double xDir = player.position().x - livingEntity.position().x;
                double zDir = player.position().z - livingEntity.position().z;
                livingEntity.knockback(DASH_HIT_KNOCKBACK, xDir, zDir);
                livingEntity.hurt(livingEntity.damageSources().generic(), DASH_HIT_DAMAGE);
            }
        }
    }

    public void onLivingAttack(LivingAttackEvent event) {
        if (isDashing) {
            event.setCanceled(true);
        }
    }
}
