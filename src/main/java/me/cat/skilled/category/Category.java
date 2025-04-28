package me.cat.skilled.category;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.NodeManager;
import me.cat.skilled.category.node.Connection;
import me.cat.skilled.category.node.Node;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class Category {
    protected static final Grid DEFAULT_GRID = new Grid(8);

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

    public void addNode(Node node) {
        String id = node.getNodeId();
        nodes.put(id, node);
        nodeToConnections.put(id, new ArrayList<>());
    }

    public void addBidirectionalConnections(Connection.ConnectionType type, List<Node> nodes) {
        int n = nodes.size();
        for (int i = 0; i < n; i++) {
            Node a = nodes.get(i);
            for (int j = i + 1; j < n; j++) {
                Node b = nodes.get(j);
                addBidirectionalConnection(type, a, b);
            }
        }
    }

    public void addManyToOneConnections(Connection.ConnectionType type, Node from, List<Node> to) {
        to.forEach(t -> addDirectionalConnection(type, from, t));
    }

    public void addBidirectionalConnection(Connection.ConnectionType type, Node a, Node b) {
        if (nodeToConnections.containsKey(a.getNodeId()) && nodeToConnections.containsKey(b.getNodeId())) {
            nodeToConnections.get(a.getNodeId()).add(new Connection(b.getNodeId(), type));
            nodeToConnections.get(b.getNodeId()).add(new Connection(a.getNodeId(), type));
        }
    }

    public void addDirectionalConnection(Connection.ConnectionType type, Node from, Node to) {
        if (nodeToConnections.containsKey(from.getNodeId()) && nodeToConnections.containsKey(to.getNodeId())) {
            nodeToConnections.get(from.getNodeId()).add(new Connection(to.getNodeId(), type));
        }
    }

    public boolean isNodeUnlocked(Player player, Node node) {
        String nodeId = node.getNodeId();
        boolean flag = false;
        for (Connection connection : nodeToConnections.get(nodeId)) {
            String connectionId = connection.connectedNodeId();
            switch (connection.type()) {
                case MUTUAL -> {
                    if (NodeManager.hasNode(player, connectionId)) {
                        flag = true;
                    }
                }
                case REQUIRED -> {
                    if (!NodeManager.hasNode(player, connectionId)) {
                        return false;
                    }
                    flag = true;
                }
                case EXCLUSIVE -> {
                    if (NodeManager.hasNode(player, connectionId)) {
                        return false;
                    }
                    flag = true;
                }
            }
        }
        return node.isRoot() || flag;
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
