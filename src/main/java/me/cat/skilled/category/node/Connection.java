package me.cat.skilled.category.node;

import me.cat.skilled.Skilled;

public record Connection(String connectedNodeId, me.cat.skilled.category.node.Connection.ConnectionType type) {
    public enum ConnectionType {
        STANDARD,
        REQUIRED,
        EXCLUSIVE;

        public static ConnectionType fromString(String string) {
            return switch(string) {
                case "standard" -> ConnectionType.STANDARD;
                case "required" -> ConnectionType.REQUIRED;
                case "exclusive" -> ConnectionType.EXCLUSIVE;
                default -> {
                    Skilled.LOGGER.error("Unknown connection type: " + string);
                    yield ConnectionType.STANDARD;
                }
            };
        }
    }
}
