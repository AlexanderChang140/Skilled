package me.cat.skilled.skill.paladin.active;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.DamageSourceRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.TickTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

public class DivineSmiteSkill extends ActiveSkill {
    private static final float DAMAGE = 6.0f;

    private final TickTimer toggleTimer = new TickTimer(20);
    private boolean canToggle = true;
    private boolean isToggled = false;

    protected DivineSmiteSkill() {
        super(100);
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        if (canToggle) {
            isToggled = !isToggled;
            canToggle = false;
        }
        return false;
    }

    @Mod.EventBusSubscriber
    public static class DivineSmiteEvents {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (PlayerSkillManager.getSkillInstance(event.player, SkillRegistry.DIVINE_SMITE.getSkillId()) instanceof DivineSmiteSkill skill
                    && !skill.canToggle
                    && skill.toggleTimer.doTick()) {
                skill.canToggle = true;
            }

            if (PlayerSkillManager.getSkillInstance(event.player, SkillRegistry.DIVINE_SMITE.getSkillId()) instanceof DivineSmiteSkill skill) {
                System.out.println(skill.isToggled);
            }

        }

        @SubscribeEvent
        public static void onAttackEntity(LivingAttackEvent event) {
            if (event.getSource().getEntity() instanceof Player player
                    && PlayerSkillManager.getSkillInstance(player, SkillRegistry.DIVINE_SMITE.getSkillId()) instanceof DivineSmiteSkill skill
                    && skill.isToggled) {
                skill.isToggled = false;
                skill.isSkillReady = false;
                if (player instanceof ServerPlayer serverPlayer) {
                    event.getEntity().hurt(DamageSourceRegistry.holyDamage(serverPlayer), event.getAmount() + DAMAGE);
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public CompoundTag saveNbt() {
        CompoundTag tag = super.saveNbt();
        tag.putInt("toggle_timer", toggleTimer.getTickCounter());
        tag.putBoolean("can_toggle", canToggle);
        tag.putBoolean("is_toggled", isToggled);
        return tag;
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        super.loadNbt(tag);
        toggleTimer.setTickCounter(tag.getInt("toggle_timer"));
        canToggle = tag.getBoolean("can_toggle");
        isToggled = tag.getBoolean("is_toggled");
    }

    @Override
    public int getCurrTick() {
        return isToggled ? -1 : super.getCurrTick();
    }

    @Override
    public boolean showCooldown() {
        return isToggled || super.showCooldown();
    }

    @Override
    public Color getCooldownColorValues() {
        return isToggled ? Color.YELLOW : super.getCooldownColorValues();
    }
}
