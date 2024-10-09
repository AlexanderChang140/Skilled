package me.cat.skilled.capability;

import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.skill.SkillFactory;
import me.cat.skilled.skill.SkillWrapper;
import net.minecraft.nbt.CompoundTag;
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
            skillMap.put(skillId, new SkillWrapper(level, SkillFactory.getSkill(skillId)));
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

    public void copyFrom(PlayerSkills source) {
        this.skillMap = source.skillMap;
        this.primarySkillId = source.primarySkillId;
    }

    public void saveNBTData(CompoundTag nbt) {
        CompoundTag mapTag = new CompoundTag();
        for (Map.Entry<String, SkillWrapper> entry : skillMap.entrySet()) {
            mapTag.putInt(entry.getKey(), entry.getValue().getSkillLevel());
        }
        nbt.put("skills", mapTag);
        nbt.putString("primary_skill_id", primarySkillId);
    }

    public void loadNBTData(CompoundTag nbt) {
        skillMap.clear();
        CompoundTag mapTag = nbt.getCompound("skills");
        for (String key : mapTag.getAllKeys()) {
            int value = mapTag.getInt(key);
            skillMap.put(key, new SkillWrapper(value, SkillFactory.getSkill(key)));
        }
        primarySkillId = nbt.getString("primary_skill_id");
    }
}
