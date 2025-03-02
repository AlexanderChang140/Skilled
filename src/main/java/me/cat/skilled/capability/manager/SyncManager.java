package me.cat.skilled.capability.manager;

import me.cat.skilled.capability.SkillProvider;
import net.minecraft.server.level.ServerPlayer;

public class SyncManager {
    public static void syncSkillCap(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(SkillProvider.SKILLS)
                .ifPresent(skills -> skills.syncCapability(serverPlayer));
    }
}
