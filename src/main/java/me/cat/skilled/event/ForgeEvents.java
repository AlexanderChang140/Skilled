package me.cat.skilled.event;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.SkillProvider;
import me.cat.skilled.skill.instance.ActiveSkill;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
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
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SkillUtil.syncSkillCap(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(SkillProvider.SKILLS).ifPresent(
                oldStore -> event.getEntity().getCapability(SkillProvider.SKILLS).ifPresent(
                        newStore -> newStore.copyFrom(oldStore)));
        event.getOriginal().invalidateCaps();

        if ((event.getEntity() instanceof ServerPlayer serverPlayer)){
            serverPlayer.getServer().execute(() -> SkillUtil.syncSkillCap(serverPlayer));
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if ((event.getEntity() instanceof ServerPlayer serverPlayer)){
            SkillUtil.syncSkillCap(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.isAlive()) {
            return;
        }

        Collection<String> activeSkillIds = SkillUtil.getActiveSkillIds(player);
        if (activeSkillIds == null) {
            return;
        }

        for (String activeSkillId : activeSkillIds) {
            if (SkillUtil.getSkillInstance(player, activeSkillId) instanceof ActiveSkill activeSkill) {
                activeSkill.checkSkillReady();
            }
        }
    }
}
