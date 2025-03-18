package me.cat.skilled.capability;

import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.out.SyncNodeCapS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Map;

@AutoRegisterCapability
public class NodeCap implements ICapability {
    private Map<String, Integer> nodes;

    public int getNodeLevel(String nodeId) {
        return nodes.getOrDefault(nodeId, 0);
    }

    public void copyFrom(NodeCap source) {
        nodes = source.nodes;
    }

    public void syncCapability(ServerPlayer serverPlayer) {
        Messenger.sendToPlayer(new SyncNodeCapS2CPacket(this), serverPlayer);
    }

    public void saveNBTData(CompoundTag tag) {

    }

    public void loadNBTData(CompoundTag tag) {

    }
}
