package me.cat.skilled.category;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.NodeManager;
import me.cat.skilled.category.node.Connection;
import me.cat.skilled.category.node.Node;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class Category {
    private static final String DEFAULT_ICON_PATH = "textures/category/";

    private final String id;
    private final String title;
    private final String description;
    private final ResourceLocation icon;

    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, List<Connection>> nodeToConnections = new HashMap<>();

    public Category(String id, String title, String description, ResourceLocation icon) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public Category(String id, String iconFileName, String title, String description) {
        this.id = id;
        this.icon = new ResourceLocation(Skilled.MODID, DEFAULT_ICON_PATH + iconFileName);
        this.title = title;
        this.description = description;
    }

    public void addNode(Node node) {
        String id = node.getNodeId();
        nodes.put(id, node);
        nodeToConnections.put(id, new ArrayList<>());
    }

    public void addConnections(List<String> nodes, Connection.ConnectionType type) {
        int n = nodes.size();
        for (int i = 0; i < n; i++) {
            String a = nodes.get(i);
            for (int j = i + 1; j < n; j++) {
                String b = nodes.get(j);
                addConnection(a, b, type);
            }
        }
    }

    private void addConnection(String a, String b, Connection.ConnectionType type) {
        if (nodeToConnections.containsKey(a) && nodeToConnections.containsKey(b)) {
            nodeToConnections.get(a).add(new Connection(b, type));
            nodeToConnections.get(b).add(new Connection(a, type));
        }
    }

    public boolean isNodeUnlocked(Player player, Node node) {
        String nodeId = node.getNodeId();
        boolean flag = false;
        for (Connection connection : nodeToConnections.get(nodeId)) {
            switch (connection.type()) {
                case STANDARD -> {
                    if (NodeManager.hasNode(player, nodeId)) flag = true;
                }
                case REQUIRED -> {
                    if (!NodeManager.hasNode(player, nodeId)) return false;
                }
                case EXCLUSIVE -> {
                    if (NodeManager.hasNode(player, nodeId)) return false;
                }
            }
        }
        return nodeToConnections.get(nodeId).isEmpty() || flag;
    }

    public boolean nodeExists(Node node) {
        return nodes.containsKey(node.getNodeId());
    }

    public String getId() {
        return id;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Node getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    public Collection<Map.Entry<String, List<Connection>>> getNodeToConnections() {
        return Collections.unmodifiableCollection(nodeToConnections.entrySet());
    }
}
