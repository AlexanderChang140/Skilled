package me.cat.skilled.event;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.PlayerSkillsProvider;
import me.cat.skilled.skill.ActiveSkill;
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


@Mod.EventBusSubscriber
public class ForgeEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerSkillsProvider.PLAYER_SKILLS).isPresent()) {
                event.addCapability(new ResourceLocation(Skilled.MODID, "properties"), new PlayerSkillsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SkillUtil.syncCapability(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(
                    oldStore -> event.getEntity().getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(
                            newStore -> newStore.copyFrom(oldStore)));
            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    private static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer) {
            ActiveSkill activeSkill = SkillUtil.getPrimarySkillInstance(serverPlayer);
            if (activeSkill != null) {
                activeSkill.checkSkillReady(event);
            }
        }
    }
}
