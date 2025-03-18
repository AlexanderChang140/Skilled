package me.cat.skilled.category.node;

import net.minecraft.server.level.ServerPlayer;

public abstract class Node {
    private final String nodeId;
    private final NodeView nodeView;
    private final int maxLevel;
    public Node(String nodeId, NodeView nodeView, int maxLevel) {
        this.nodeId = nodeId;
        this.nodeView = nodeView;
        this.maxLevel = maxLevel;
    }

    public void onAdd(ServerPlayer serverPlayer, int level) {

    }

    public void onLoad(ServerPlayer serverPlayer, int level) {

    }

    public void onRemove(ServerPlayer serverPlayer) {

    }

    public String getNodeId() {
        return nodeId;
    }

    public NodeView getNodeView() {
        return nodeView;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
