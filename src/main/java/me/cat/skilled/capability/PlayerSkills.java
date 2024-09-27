package me.cat.skilled.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.HashMap;
import java.util.Map;

@AutoRegisterCapability
public class PlayerSkills {

    private Map<String, Integer> skillMap = new HashMap<>();

    public boolean hasSkill(String skillId) {
        return skillMap.containsKey(skillId) && getSkillLevel(skillId) > 0;
    }

    public int getSkillLevel(String skillId) {
        return skillMap.get(skillId);
    }

    public void setSkillLevel(String skillId, int level) {
        skillMap.put(skillId, level);
    }

    public void clearMap() {
        skillMap.clear();
    }

    public void copyFrom(PlayerSkills source) {
        this.skillMap = source.skillMap;
    }

    public void saveNBTData(CompoundTag nbt) {
        for (Map.Entry<String, Integer> entry : skillMap.entrySet()) {
            nbt.putInt(entry.getKey(), entry.getValue());
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        skillMap.clear();

        for (String key : nbt.getAllKeys()) {
            int value = nbt.getInt(key);
            skillMap.put(key,value);
        }
    }
}
