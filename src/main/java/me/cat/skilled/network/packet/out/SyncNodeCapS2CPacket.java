package me.cat.skilled.network.packet.out;

import me.cat.skilled.capability.NodeCap;
import me.cat.skilled.capability.NodeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncNodeCapS2CPacket {

    private final CompoundTag nodeTag;

    public SyncNodeCapS2CPacket(NodeCap nodeCap) {
        CompoundTag tag = new CompoundTag();
        nodeCap.saveNBTData(tag);
        nodeTag = tag;
    }

    public SyncNodeCapS2CPacket(FriendlyByteBuf buf) {
        this.nodeTag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(nodeTag);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            localPlayer.getCapability(NodeProvider.NODES)
                    .ifPresent(skills -> skills.loadNBTData(nodeTag));
        });
    }
}
