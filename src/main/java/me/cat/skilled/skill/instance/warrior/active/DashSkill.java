package me.cat.skilled.skill.instance.warrior.active;

import me.cat.skilled.skill.instance.ActiveSkill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.util.SkillUtil;
import me.cat.skilled.util.TickTimer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;

@Mod.EventBusSubscriber
public class DashSkill extends ActiveSkill {
    public static final int MAX_LEVEL = 1;

    public static final double DASH_SPEED = 1.5;
    private static final double DASH_HIT_RADIUS = 1.5;
    private static final float DASH_HIT_DAMAGE = 5.0f;
    private static final double DASH_HIT_KNOCKBACK = 1.0;

    private final TickTimer dashDuration = new TickTimer(20);
    private final HashSet<Entity> hitSet = new HashSet<>();
    private boolean isDashing = false;

    public DashSkill() {
        super(200);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    protected void onActivateSkill(ServerPlayer serverPlayer) {
        isDashing = true;

        double DASH_SPEED = DashSkill.DASH_SPEED;
        Vec3 motion = serverPlayer.getDeltaMovement();
        Vec3 lookDirection = serverPlayer.getLookAngle();
        Vec3 dashVelocity = new Vec3(
                lookDirection.x * DASH_SPEED,
                lookDirection.y * DASH_SPEED,
                lookDirection.z * DASH_SPEED
        );

        serverPlayer.setDeltaMovement(motion.add(dashVelocity));
        serverPlayer.fallDistance = 0.0F;
        serverPlayer.hurtMarked = true;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!(event.player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.getSkillInstance(serverPlayer, SkillRegistry.DASH.getSkillId()) instanceof DashSkill dashSkill)) {
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
            LivingEntity livingEntity = (LivingEntity) entity;
            double xDir = serverPlayer.position().x - livingEntity.position().x;
            double zDir = serverPlayer.position().z - livingEntity.position().z;
            if (Math.hypot(xDir, zDir) <= DASH_HIT_RADIUS) {
                livingEntity.knockback(DASH_HIT_KNOCKBACK, xDir, zDir);
                livingEntity.hurt(livingEntity.damageSources().playerAttack(serverPlayer), DASH_HIT_DAMAGE);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(SkillUtil.getSkillInstance(serverPlayer, SkillRegistry.DASH.getSkillId()) instanceof DashSkill dashSkill)) {
            return;
        }

        if (dashSkill.isDashing) {
            event.setCanceled(true);
        }
    }
}
