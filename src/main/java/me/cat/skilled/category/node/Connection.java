package me.cat.skilled.category.node;

import me.cat.skilled.Skilled;

public record Connection(String connectedNodeId, me.cat.skilled.category.node.Connection.ConnectionType type) {
    public enum ConnectionType {
        MUTUAL,
        REQUIRED,
        EXCLUSIVE;

        public static ConnectionType fromString(String string) {
            return switch(string) {
                case "standard" -> ConnectionType.MUTUAL;
                case "required" -> ConnectionType.REQUIRED;
                case "exclusive" -> ConnectionType.EXCLUSIVE;
                default -> {
                    Skilled.LOGGER.error("Unknown connection type: " + string);
                    yield ConnectionType.MUTUAL;
                }
            };
        }
    }
}
