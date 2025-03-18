package me.cat.skilled.category.node;

public class Connection {
    private final String connectedNodeId;
    private final ConnectionType type;

    public enum ConnectionType {
        STANDARD,
        REQUIRED,
        EXCLUSIVE
    }

    public Connection(String connectedNodeId, ConnectionType type) {
        this.connectedNodeId = connectedNodeId;
        this.type = type;
    }

    public String getConnectedNodeId() {
        return connectedNodeId;
    }

    public ConnectionType getType() {
        return type;
    }
}
