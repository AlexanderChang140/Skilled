package me.cat.skilled.capability;

import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.SyncCapabilityS2CPacket;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.skill.SkillFactory;
import me.cat.skilled.skill.SkillWrapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.HashMap;
import java.util.Map;

@AutoRegisterCapability
public class PlayerSkills {

    private Map<String, SkillWrapper> skillMap = new HashMap<>();
    private String primarySkillId = "";

    public boolean hasSkill(String skillId) {
        return skillMap.containsKey(skillId);
    }

    public int getSkillLevel(String skillId) {
        return skillMap.get(skillId).getSkillLevel();
    }

    public Skill getSkillInstance(String skillId) {
        if (skillMap.containsKey(skillId)) {
            return skillMap.get(skillId).getSkillInstance();
        }
        return null;
    }

    public void updateSkill(String skillId, int level) {
        if (skillMap.containsKey(skillId)) {
            if (level == 0) {
                skillMap.remove(skillId);
            }
            else {
                skillMap.get(skillId).setSkillLevel(level);
            }
        }
        else {
            Skill skillInstance = SkillFactory.getSkill(skillId);
            if (skillInstance != null) {
                skillMap.put(skillId, new SkillWrapper(level, skillInstance));
            }
        }
    }

    public void clearSkills() {
        primarySkillId = "";
        skillMap.clear();
    }

    public String getPrimarySkillId() {
        return primarySkillId;
    }

    public void setPrimarySkillId(String skillId) {
        Skill skillInstance = skillMap.get(skillId).getSkillInstance();
        if (skillInstance instanceof ActiveSkill) {
            primarySkillId = skillId;
        }
        else if (skillInstance == null) {
            throw new RuntimeException("Attempted to set primarySkill to non-ActiveSkill");

        }
        else {
            throw new RuntimeException("Player does not possess skill");
        }
    }

    public Map<String, SkillWrapper> getMap() {
        return skillMap;
    }

    public void syncCapability(ServerPlayer serverPlayer) {
        Messenger.sendToPlayer(new SyncCapabilityS2CPacket(this), serverPlayer);
    }

    public void copyFrom(PlayerSkills source) {
        this.skillMap = source.skillMap;
        this.primarySkillId = source.primarySkillId;
    }

    public void saveNBTData(CompoundTag nbt) {
        CompoundTag skillLevelTag = new CompoundTag();
        CompoundTag skillDataTag = new CompoundTag();
        for (Map.Entry<String, SkillWrapper> entry : skillMap.entrySet()) {
            String skillId = entry.getKey();
            SkillWrapper wrapper = entry.getValue();

            skillLevelTag.putInt(skillId, wrapper.getSkillLevel());
            if (wrapper.getSkillInstance() instanceof SerializedSkill serializedSkill) {
                skillDataTag.put(skillId, serializedSkill.saveNbt());
            }
        }
        nbt.put("skill_level", skillLevelTag);
        nbt.put("skill_data", skillDataTag);
        nbt.putString("primary_skill_id", primarySkillId);
    }

    public void loadNBTData(CompoundTag nbt) {
        skillMap.clear();
        CompoundTag skillLevelTag = nbt.getCompound("skill_level");
        CompoundTag skillDataTag = nbt.getCompound("skill_data");
        for (String skillId : skillLevelTag.getAllKeys()) {
            int level = skillLevelTag.getInt(skillId);
            Skill skillInstance = SkillFactory.getSkill(skillId);

            if (skillInstance instanceof SerializedSkill serializedSkill) {
                serializedSkill.loadNbt(skillDataTag.getCompound(skillId));
            }

            skillMap.put(skillId, new SkillWrapper(level, skillInstance));
        }
        primarySkillId = nbt.getString("primary_skill_id");
    }
}
