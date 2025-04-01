package me.cat.skilled.capability.manager;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.NodeCap;
import me.cat.skilled.category.Category;
import me.cat.skilled.category.node.Node;
import me.cat.skilled.registry.CapabilityRegistry;
import me.cat.skilled.registry.CategoryRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.Map;

public class NodeManager {
    public static void updateNode(ServerPlayer serverPlayer, String nodeId, int level) {
        Node node = getNode(serverPlayer, nodeId);
        int currLevel = getNodeLevel(serverPlayer, nodeId);
        int skillPoints = currLevel - level;
        PlayerLevelManager.addSkillPoints(serverPlayer, skillPoints);
        if (level > 0) {
            node.onAdd(serverPlayer, level);
            node.onLoad(serverPlayer, level);
        }
        else {
            node.onRemove(serverPlayer, level);
        }
        serverPlayer.getCapability(CapabilityRegistry.NODES)
                .ifPresent(nodes -> nodes.updateNode(nodeId, level));
        SyncManager.syncCapability(serverPlayer, CapabilityRegistry.NODES);
    }

    public static int getNodeLevel(Player player, String nodeId) {
        return player.getCapability(CapabilityRegistry.NODES)
                .map(nodes -> nodes.getNodeLevel(nodeId))
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve node level for player: " + player.getName().getString() + " node: " + nodeId);
                    return 0;
                });
    }

    public static boolean hasNode(Player player, String nodeId) {
        return getNodeLevel(player, nodeId) == 0;
    }

    public static Node getNode(Player player, String nodeId) {
        return getCategory(player).getNode(nodeId);
    }

    public static Collection<Map.Entry<String, Integer>> getNodes(Player player) {
        return player.getCapability(CapabilityRegistry.NODES)
                .map(NodeCap::getNodes)
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve nodes for player: " + player.getName().getString());
                    return null;
                });
    }

    public static boolean canAcquireNode(Player player, Node node) {
        Category category = getCategory(player);
        return category.nodeExists(node)
                && category.isNodeUnlocked(player, node)
                && NodeManager.getNodeLevel(player, node.getNodeId()) < node.getMaxLevel()
                && PlayerLevelManager.getSkillPoints(player) >= node.getCost()
                && PlayerLevelManager.getUsedSkillPoints(player) >= node.getRequiredPoints();
    }

    public static void clearNodes(ServerPlayer serverPlayer) {
        PlayerLevelManager.resetSkillPoints(serverPlayer);
        for (var entry : getNodes(serverPlayer)) {
            String nodeId = entry.getKey();
            int nodeLevel = entry.getValue();
            Node node = getNode(serverPlayer, nodeId);
            node.onRemove(serverPlayer, nodeLevel);
        }
        serverPlayer.getCapability(CapabilityRegistry.NODES)
                .ifPresent(NodeCap::clearNodes);
        SyncManager.syncCapability(serverPlayer, CapabilityRegistry.NODES);
    }

    public static void clearAll(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(CapabilityRegistry.NODES)
                .ifPresent(NodeCap::clearCategory);
        clearNodes(serverPlayer);
    }

    public static String getCategoryId(Player player) {
        return player.getCapability(CapabilityRegistry.NODES)
                .map(NodeCap::getCategory)
                .orElseGet(() -> {
                    Skilled.LOGGER.error("Failed to retrieve category id for player: " + player.getName().getString());
                    return "";
                });
    }

    public static void setCategoryId(ServerPlayer serverPlayer, String categoryId) {
        serverPlayer.getCapability(CapabilityRegistry.NODES)
                .ifPresent(nodes -> nodes.setCategory(categoryId));
        SyncManager.syncCapability(serverPlayer, CapabilityRegistry.NODES);
    }

    public static Category getCategory(Player player) {
        return CategoryRegistry.getCategory(getCategoryId(player));
    }
}
