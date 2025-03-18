package me.cat.skilled.capability.manager;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.NodeProvider;
import net.minecraft.server.level.ServerPlayer;

public class NodeManager {
    public static int getNodeLevel(ServerPlayer serverPlayer, String nodeId) {
        return serverPlayer.getCapability(NodeProvider.NODES)
                .map(nodes -> nodes.getNodeLevel(nodeId))
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve nod level for player: " + serverPlayer.getName() + " node: " + nodeId);
                    return 0;
                });
    }
}
