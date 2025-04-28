package me.cat.skilled.skill.skills.rogue.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.out.ClearShaderS2C;
import me.cat.skilled.network.packet.out.LoadShaderS2C;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.DurationSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class VoidwalkerSkill extends DurationSkill {
    private static final ResourceLocation GRAYSCALE_SHADER = new ResourceLocation(Skilled.MODID, "shaders/post/grayscale.json");

    public VoidwalkerSkill() {
        super(100, 100, true);
    }


    @Override
    protected void onStart(ServerPlayer serverPlayer) {
        super.onStart(serverPlayer);
        Messenger.sendToPlayer(new LoadShaderS2C(GRAYSCALE_SHADER), serverPlayer);
    }

    @Override
    protected void onCancel(ServerPlayer serverPlayer) {
        super.onCancel(serverPlayer);
        onEnd(serverPlayer);
    }

    @Override
    public void onEnd(ServerPlayer serverPlayer) {
        super.onEnd(serverPlayer);
        Messenger.sendToPlayer(new ClearShaderS2C(), serverPlayer);
    }

    @Mod.EventBusSubscriber
    public static class VoidwalkerEventHandler {
        // Prevent living entities from rendering
        @SubscribeEvent
        public static void onLivingEntityRender(RenderLivingEvent.Pre<?, ?> event) {
            if (PlayerSkillManager.getSkillInstance(Minecraft.getInstance().player, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isActive);
            }

            if (event.getEntity() instanceof Player player
                    && PlayerSkillManager.getSkillInstance(player, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isActive);
            }
        }

        // Prevent player from being targeted by entities
        @SubscribeEvent
        public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
            if (event.getNewTarget() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isActive);
            }
        }

        // Prevent player from taking damage
        @SubscribeEvent
        public static void onLivingDamageEvent(LivingDamageEvent event) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isActive);
            }
        }

        // Prevent player from attacking entities
        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer
                    && PlayerSkillManager.getSkillInstance(serverPlayer, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isActive);
            }
        }

        // Prevent player from interacting with world and entities
        @SubscribeEvent
        public static void onPlayerInteract(PlayerInteractEvent event) {
            if (event.isCancelable() && PlayerSkillManager.getSkillInstance(event.getEntity(), SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
                event.setCanceled(voidwalkerSkill.isActive);
            }
        }
    }
}
