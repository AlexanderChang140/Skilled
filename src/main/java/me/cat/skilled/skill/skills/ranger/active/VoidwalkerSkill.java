package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.out.ClearShaderS2C;
import me.cat.skilled.network.packet.out.LoadShaderS2C;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.TickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

public class VoidwalkerSkill extends ActiveSkill {
    private static final ResourceLocation GRAYSCALE_SHADER = new ResourceLocation(Skilled.MODID, "shaders/post/grayscale.json");

    private final TickTimer voidwalkTimer = new TickTimer(100);
    private boolean isVoidwalking = false;

    public VoidwalkerSkill() {
        super(100);
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        if (!isVoidwalking) {
            isVoidwalking = true;
            voidwalkTimer.setTickCounter(0);
            Messenger.sendToPlayer(new LoadShaderS2C(GRAYSCALE_SHADER), serverPlayer);
        }
        return !isVoidwalking;
    }

    @Mod.EventBusSubscriber
    public static class VoidwalkerEventHandler {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (PlayerSkillManager.getSkillInstance(event.player, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill
                    && voidwalkerSkill.isVoidwalking
                    && voidwalkerSkill.voidwalkTimer.doTick()) {
                voidwalkerSkill.isVoidwalking = false;
                if (event.player instanceof ServerPlayer serverPlayer) {
                    Messenger.sendToPlayer(new ClearShaderS2C(), serverPlayer);
                }
            }
        }

        // Prevent living entities from rendering
        @SubscribeEvent
        public static void onLivingEntityRender(RenderLivingEvent.Pre<?, ?> event) {
            if (PlayerSkillManager.getSkillInstance(Minecraft.getInstance().player, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isVoidwalking);
            }

            if (event.getEntity() instanceof Player player
                    && PlayerSkillManager.getSkillInstance(player, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isVoidwalking);
            }
        }

        // Prevent player from being targeted by entities
        @SubscribeEvent
        public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
            if (event.getNewTarget() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isVoidwalking);
            }
        }

        // Prevent player from taking damage
        @SubscribeEvent
        public static void onLivingDamageEvent(LivingDamageEvent event) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isVoidwalking);
            }
        }

        // Prevent player from attacking entities
        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isVoidwalking);
            }
        }

        // Prevent player from interacting with world and entities
        @SubscribeEvent
        public static void onPlayerInteract(PlayerInteractEvent event) {
            if (event.isCancelable() && PlayerSkillManager.getSkillInstance(event.getEntity(), SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isVoidwalking);
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putInt("voidwalkTimer", voidwalkTimer.getTickCounter());
        tag.putBoolean("is_voidwalking", isVoidwalking);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        voidwalkTimer.setTickCounter(tag.getInt("voidwalkTimer"));
        isVoidwalking = tag.getBoolean("is_voidwalking");
    }

    @Override
    public boolean doSkillTimer() {
        return !isVoidwalking;
    }

    @Override
    public int getCurrTick() {
        return isVoidwalking ? voidwalkTimer.getTickCounter() : super.getCurrTick();
    }

    @Override
    public int getMaxTick() {
        return isVoidwalking ? voidwalkTimer.getTicksPerAction() : super.getMaxTick();
    }

    @Override
    public boolean showCooldown() {
        return isVoidwalking || super.showCooldown();
    }

    @Override
    public Color getCooldownColorValues() {
        return isVoidwalking ? Color.YELLOW : super.getCooldownColorValues();
    }

    public boolean isVoidwalking() {
        return isVoidwalking;
    }
}
