package me.cat.skilled.event;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.NodeProvider;
import me.cat.skilled.capability.SkillProvider;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.capability.manager.SyncManager;
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
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;


@Mod.EventBusSubscriber
public class ForgeEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !event.getObject().getCapability(SkillProvider.SKILLS).isPresent()) {
            event.addCapability(new ResourceLocation(Skilled.MODID, "properties"), new SkillProvider());
            event.addCapability(new ResourceLocation(Skilled.MODID, "properties"), new NodeProvider());
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
        event.getOriginal().getCapability(SkillProvider.SKILLS).ifPresent(
                oldStore -> event.getEntity().getCapability(SkillProvider.SKILLS).ifPresent(
                        newStore -> newStore.copyFrom(oldStore)));
        event.getOriginal().getCapability(NodeProvider.NODES).ifPresent(
                oldStore -> event.getEntity().getCapability(NodeProvider.NODES).ifPresent(
                        newStore -> newStore.copyFrom(oldStore)));
        event.getOriginal().invalidateCaps();

        if ((event.getEntity() instanceof ServerPlayer serverPlayer)){
            serverPlayer.getServer().execute(() -> SyncManager.syncCaps(serverPlayer));
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
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getMobType() == MobType.UNDEAD && event.getSource().is(DamageSourceRegistry.HOLY)) {
            event.setAmount(event.getAmount() * 2);
        }
    }
}
