package me.cat.skilled.capability;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.*;

@AutoRegisterCapability
public class SkillCap extends CapabilityInstance implements ISkillCap {
    private Map<String, Skill> skillMap = new HashMap<>();
    private Map<SkillSlot, String> activeSkills = new EnumMap<>(SkillSlot.class);

    @Override
    public boolean hasSkill(String skillId) {
        return skillMap.containsKey(skillId);
    }

    @Override
    public int getSkillLevel(String skillId) {
        return skillMap.containsKey(skillId) ? skillMap.get(skillId).getLevel() : 0;
    }

    @Override
    public Skill getSkillInstance(String skillId) {
        return skillMap.getOrDefault(skillId, null);
    }

    @Override
    public void updateSkill(String skillId, int level) {
        if (level == 0) {
            removeSkill(skillId);
            return;
        }

        if (SkillRegistry.getSkillData(skillId) instanceof ActiveSkillData activeSkillData) {
            SkillSlot skillSlot = activeSkillData.getSkillSlot();
            if (activeSkills.get(skillSlot) != null) {
                removeSkill(getActiveSkillId(skillSlot));
            }
            activeSkills.put(skillSlot, skillId);
        }

        skillMap.compute(skillId, (id, skill) -> {
            if (skill == null) {
                skill = SkillRegistry.getSkillData(id).getSkillInstance();
            }
            skill.setLevel(level);
            return skill;
        });
    }

    @Override
    public void removeSkill(String skillId) {
        skillMap.remove(skillId);
        if (SkillRegistry.getSkillData(skillId) instanceof ActiveSkillData activeSkillData) {
            activeSkills.put(activeSkillData.getSkillSlot(), null);
        }
    }

    @Override
    public Collection<String> getActiveSkills() {
        return activeSkills.values();
    }

    @Override
    public String getActiveSkillId(SkillSlot skillSlot) {
        return activeSkills.get(skillSlot);
    }

    @Override
    public void clearSkills() {
        activeSkills.replaceAll((k, v) -> null);
        skillMap.clear();
    }

    @Override
    public Map<String, Skill> getSkillMap() {
        return Collections.unmodifiableMap(skillMap);
    }

    @Override
    public Map<SkillSlot, String> getActiveSkillMap() {
        return Collections.unmodifiableMap(activeSkills);
    }

    @Override
    public void copyFrom(CapabilityInstance capabilityInstance) {
        SkillCap source = (SkillCap) capabilityInstance;
        skillMap = source.skillMap;
        activeSkills = source.activeSkills;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        CompoundTag skillDataTag = new CompoundTag();
        for (Map.Entry<String, Skill> entry : skillMap.entrySet()) {
            String skillId = entry.getKey();
            Skill skillInstance = entry.getValue();
            skillDataTag.put(skillId, skillInstance.serializeNBT());
        }

        CompoundTag activeSkillsTag = new CompoundTag();
        for (Map.Entry<SkillSlot, String> entry : activeSkills.entrySet()) {
            int index = entry.getKey().getIndex();
            String skillId = entry.getValue();
            if (entry.getValue() != null) {
                activeSkillsTag.putString(Integer.toString(index), skillId);
            }
        }

        nbt.put("skill_data", skillDataTag);
        nbt.put("active_skills", activeSkillsTag);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        skillMap.clear();
        activeSkills.clear();
        CompoundTag skillDataTag = nbt.getCompound("skill_data");
        for (String skillId : skillDataTag.getAllKeys()) {
            if (PlayerSkillManager.skillExists(skillId)) {
                Skill skillInstance = SkillRegistry.getSkillData(skillId).getSkillInstance();
                skillInstance.deserializeNBT(skillDataTag.getCompound(skillId));
                skillMap.put(skillId, skillInstance);
            }
            else {
                Skilled.LOGGER.error("Attempted to load invalid skill");
            }
        }

        CompoundTag activeSkillsTag = nbt.getCompound("active_skills");
        for (String key : activeSkillsTag.getAllKeys()) {
            if (PlayerSkillManager.skillExists(activeSkillsTag.getString(key))) {
                int index = Integer.parseInt(key);
                activeSkills.put(SkillSlot.fromNumber(index), activeSkillsTag.getString(key));
            }
            else {
                Skilled.LOGGER.error("Attempted to load invalid active skill");
            }
        }
    }
}
