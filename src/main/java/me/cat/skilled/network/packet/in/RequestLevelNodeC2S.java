package me.cat.skilled.network.packet.in;

import me.cat.skilled.capability.manager.NodeManager;
import me.cat.skilled.category.node.Node;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestLevelNodeC2S {
    private final String nodeId;
    private final int level;

    public RequestLevelNodeC2S(Node node, int level) {
        this.nodeId = node.getNodeId();
        this.level = level;
    }

    public RequestLevelNodeC2S(FriendlyByteBuf buf) {
        nodeId = buf.readUtf();
        level = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(nodeId);
        buf.writeInt(level);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Node node = NodeManager.getCategory(serverPlayer).getNode(nodeId);
            if (NodeManager.canAcquireNode(serverPlayer, node)) {
                NodeManager.updateNode(serverPlayer, nodeId, level + 1);
            }
        });
    }
}
