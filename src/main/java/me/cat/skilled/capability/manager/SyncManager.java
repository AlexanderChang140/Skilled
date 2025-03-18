package me.cat.skilled.capability.manager;

import me.cat.skilled.capability.NodeProvider;
import me.cat.skilled.capability.SkillProvider;
import net.minecraft.server.level.ServerPlayer;

public class SyncManager {
    public static void syncCaps(ServerPlayer serverPlayer) {
        syncSkillCap(serverPlayer);
        syncNodeCap(serverPlayer);
    }

    public static void syncSkillCap(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.syncCapability(serverPlayer));
    }

    public static void syncNodeCap(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(NodeProvider.NODES)
                .ifPresent(nodes -> nodes.syncCapability(serverPlayer));
    }
}
