package me.cat.skilled.category.node;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.in.AddSkillLevelC2SPacket;
import me.cat.skilled.registry.SkillRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Function;

public class SkillNode extends Node {
    private final String skillId;

    public SkillNode(String nodeId, NodeView nodeView, int maxLevel, String skillId) {
        super(nodeId, nodeView, maxLevel);
        this.skillId = skillId;
    }

    public SkillNode(String skillId, NodeView nodeView, int maxLevel) {
        super(skillId, nodeView, maxLevel);
        this.skillId = skillId;
    }

    @Override
    public void onAdd(ServerPlayer serverPlayer, int level) {
        PlayerSkillManager.updateSkillLevel(serverPlayer, skillId, level);
    }

    @Override
    public void onRemove(ServerPlayer serverPlayer) {
        PlayerSkillManager.updateSkillLevel(serverPlayer, skillId, 0);
    }

    public String getSkillId() {
        return skillId;
    }
}
