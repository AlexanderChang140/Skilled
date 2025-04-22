package me.cat.skilled.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AutoRegisterCapability
public class NodeCap extends CapabilityInstance {
    public static final int MAX_LEVEL = 20;

    private Map<String, Integer> nodes = new HashMap<>();
    private int playerLevel = 1;
    private int playerExperience = 0;
    private int skillPoints = 1;
    private String category = "";

    public Collection<Map.Entry<String, Integer>> getNodes() {
        return Collections.unmodifiableCollection(nodes.entrySet());
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
        setDirty(true);
    }

    public int getPlayerExperience() {
        return playerExperience;
    }

    public void setPlayerExperience(int playerExperience) {
        this.playerExperience = playerExperience;
        setDirty(true);
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
        setDirty(true);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        setDirty(true);
    }

    public void clearCategory() {
        category = "";
        setDirty(true);
    }

    public void clearNodes() {
        nodes.clear();
        setDirty(true);
    }

    public void updateNode(String nodeId, int level) {
        if (level == 0){
            nodes.remove(nodeId);
        }
        else {
            nodes.put(nodeId, level);
        }
        setDirty(true);
    }

    public int getNodeLevel(String nodeId) {
        return nodes.getOrDefault(nodeId, 0);
    }

    @Override
    public void copyFrom(CapabilityInstance capabilityInstance) {
        NodeCap source = (NodeCap) capabilityInstance;
        nodes = source.nodes;
        playerLevel = source.playerLevel;
        playerExperience = source.playerExperience;
        skillPoints = source.skillPoints;
        category = source.category;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        CompoundTag nodeDataTag = new CompoundTag();
        for (var entry : nodes.entrySet()) {
            String nodeId = entry.getKey();
            int nodeLevel = entry.getValue();
            nodeDataTag.putInt(nodeId, nodeLevel);
        }
        nbt.put("node_data", nodeDataTag);

        nbt.putInt("player_level", playerLevel);
        nbt.putInt("player_experience", playerExperience);
        nbt.putInt("skill_points", skillPoints);
        nbt.putString("category", category);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        nodes.clear();
        CompoundTag nodeDataTag = nbt.getCompound("node_data");
        for (String nodeId : nodeDataTag.getAllKeys()) {
            int nodeLevel = nodeDataTag.getInt(nodeId);
            nodes.put(nodeId, nodeLevel);
        }

        playerLevel = nbt.getInt("player_level");
        playerExperience = nbt.getInt("player_experience");
        skillPoints = nbt.getInt("skill_points");
        category = nbt.getString("category");
    }
}
