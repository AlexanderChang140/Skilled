package me.cat.skilled.category.node;

import me.cat.skilled.category.reward.Reward;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String nodeId;
    private final NodeView nodeView;
    private final boolean isRoot;
    private final int maxLevel;
    private final int requiredPoints;
    private final int cost;
    private final List<Reward> rewards = new ArrayList<>();

    public Node(String nodeId, boolean isRoot, int maxLevel, int requiredPoints, int cost, NodeView nodeView) {
        this.nodeId = nodeId;
        this.isRoot = isRoot;
        this.maxLevel = maxLevel;
        this.requiredPoints = requiredPoints;
        this.cost = cost;
        this.nodeView = nodeView;
    }

    public void addReward(Reward reward) {
        rewards.add(reward);
    }

    public void onAdd(ServerPlayer serverPlayer, int level) {
        for (var reward : rewards) {
            reward.onAdd(serverPlayer, level);
        }
    }

    public void onLoad(ServerPlayer serverPlayer, int level) {
        for (var reward : rewards) {
            reward.onLoad(serverPlayer, level);
        }
    }

    public void onRemove(ServerPlayer serverPlayer, int level) {
        for (var reward : rewards) {
            reward.onRemove(serverPlayer, level);
        }
    }

    public String getNodeId() {
        return nodeId;
    }

    public NodeView getNodeView() {
        return nodeView;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public int getCost() {
        return cost;
    }
}
