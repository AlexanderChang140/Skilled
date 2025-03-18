package me.cat.skilled.category;

import me.cat.skilled.Skilled;
import me.cat.skilled.category.node.Connection;
import me.cat.skilled.category.node.Node;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class Category {
    private static final String DEFAULT_ICON_PATH = "textures/category/";

    private final String id;
    private final ResourceLocation icon;
    private final String title;
    private final String description;

    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, List<Connection>> connections = new HashMap<>();

    public Category(String id, ResourceLocation icon, String title, String description) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    public Category(String id, String iconFileName, String title, String description) {
        this.id = id;
        this.icon = new ResourceLocation(Skilled.MODID, DEFAULT_ICON_PATH + iconFileName);
        this.title = title;
        this.description = description;
    }

    public void addNode(Node node) {
        nodes.put(node.getNodeId(), node);
    }

    public void addConnection(Node a, Node b, Connection.ConnectionType type) {
        connections.get(a.getNodeId()).add(new Connection(b.getNodeId(), type));
        connections.get(b.getNodeId()).add(new Connection(a.getNodeId(), type));
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

    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    public Collection<List<Connection>> getConnections() {
        return Collections.unmodifiableCollection(connections.values());
    }
}
