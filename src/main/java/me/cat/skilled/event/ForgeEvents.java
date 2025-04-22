package me.cat.skilled.event;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.*;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.capability.manager.SyncManager;
import me.cat.skilled.registry.CapabilityRegistry;
import me.cat.skilled.registry.DamageSourceRegistry;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.skill.ToggleableSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.Objects;


@Mod.EventBusSubscriber
public class ForgeEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            CapabilityProvider provider = new CapabilityProvider();
            provider.registerCapability(CapabilityRegistry.SKILLS, SkillCap::new);
            provider.registerCapability(CapabilityRegistry.NODES, NodeCap::new);
            event.addCapability(new ResourceLocation(Skilled.MODID, "properties"), provider);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SyncManager.syncCaps(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        for (Capability<?> capability : CapabilityRegistry.getCapabilities()) {
            event.getOriginal().getCapability(capability).ifPresent(
                    oldStore -> event.getEntity().getCapability(capability).ifPresent(
                            newStore -> {
                                if (oldStore instanceof CapabilityInstance source && newStore instanceof CapabilityInstance instance) {
                                    instance.copyFrom(source);
                                }
                            }));
        }
        event.getOriginal().invalidateCaps();
        if ((event.getEntity() instanceof ServerPlayer serverPlayer)){
            Objects.requireNonNull(serverPlayer.getServer()).execute(() -> SyncManager.syncCaps(serverPlayer));
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if ((event.getEntity() instanceof ServerPlayer serverPlayer)){
            SyncManager.syncCaps(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.isAlive()) {
            return;
        }

        Collection<String> activeSkillIds = PlayerSkillManager.getActiveSkillIds(player);
        if (activeSkillIds == null) {
            return;
        }

        for (String activeSkillId : activeSkillIds) {
            Skill skill = PlayerSkillManager.getSkillInstance(player, activeSkillId);
            if (skill instanceof ActiveSkill activeSkill) {
                activeSkill.tickSkillTimer();
                if (activeSkill instanceof ToggleableSkill toggleableSkill) {
                    toggleableSkill.tickToggleTimer();
                }
            }
        }

        if (event.phase == TickEvent.Phase.END && player instanceof ServerPlayer serverPlayer) {
            CapabilityRegistry.getCapabilities().forEach(token -> serverPlayer.getCapability(token).ifPresent(cap -> {
                if (cap instanceof CapabilityInstance instance && instance.isDirty()) {
                    SyncManager.syncCapability(serverPlayer, token);
                }
            }));
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getMobType() == MobType.UNDEAD && event.getSource().is(DamageSourceRegistry.HOLY)) {
            event.setAmount(event.getAmount() * 2);
        }
    }
}
