package me.cat.skilled.network.packet.out;

import me.cat.skilled.capability.CapabilityInstance;
import me.cat.skilled.registry.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCapabilityS2CPacket {
    private final String capabilityId;
    private CompoundTag capabilityTag;

    public SyncCapabilityS2CPacket(ServerPlayer serverPlayer, Capability<?> capability) {
        capabilityId = capability.getName();
        serverPlayer.getCapability(capability)
                .ifPresent(cap -> {
                    if (cap instanceof CapabilityInstance instance) {
                        capabilityTag = instance.serializeNBT();

                    }
                });
    }

    public SyncCapabilityS2CPacket(FriendlyByteBuf buf) {
        capabilityId = buf.readUtf();
        capabilityTag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(capabilityId);
        buf.writeNbt(capabilityTag);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            localPlayer.getCapability(CapabilityRegistry.getCapability(capabilityId))
                    .ifPresent(cap -> {
                        if (cap instanceof CapabilityInstance instance) {
                            instance.deserializeNBT(capabilityTag);
                        }
                    });
        });
    }
}
