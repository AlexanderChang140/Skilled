package me.cat.skilled.capability.manager;

import me.cat.skilled.capability.CapabilityInstance;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.out.SyncCapabilityS2CPacket;
import me.cat.skilled.network.packet.out.SyncSkillS2CPacket;
import me.cat.skilled.registry.CapabilityRegistry;
import me.cat.skilled.skill.Skill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;

public class SyncManager {
    public static void syncCaps(ServerPlayer serverPlayer) {
        for (Capability<?> capability : CapabilityRegistry.getCapabilities()) {
            syncCapability(serverPlayer, capability);
        }
    }

    public static void syncCapability(ServerPlayer serverPlayer, Capability<?> capability) {
        Messenger.sendToPlayer(new SyncCapabilityS2CPacket(serverPlayer, capability), serverPlayer);
        setDirty(serverPlayer, capability, false);
    }

    public static void syncSkill(ServerPlayer serverPlayer, Skill skill) {
        Messenger.sendToPlayer(new SyncSkillS2CPacket(skill), serverPlayer);
    }

    public static void setDirty(ServerPlayer serverPlayer, Capability<?> capability, boolean isDirty) {
        serverPlayer.getCapability(capability)
                .ifPresent(cap -> {
                    if (cap instanceof CapabilityInstance instance) {
                        instance.setDirty(isDirty);
                    }
                });
    }
}
