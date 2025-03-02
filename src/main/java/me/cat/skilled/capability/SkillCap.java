package me.cat.skilled.capability;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.out.SyncSkillCapS2CPacket;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.ExperienceUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.*;

@AutoRegisterCapability
public class SkillCap implements ISkillCap {
    private Map<String, Skill> skillMap = new HashMap<>();
    private Map<SkillSlot, String> activeSkills = new EnumMap<>(SkillSlot.class);
    private int playerLevel = 1;
    private int playerExperience = 0;
    private int experienceToNextLevel = ExperienceUtil.levelToExperience(playerLevel + 1);
    private int skillPoints = 1;
    private String category = "";

    @Override
    public int getPlayerLevel() {
        return playerLevel;
    }

    @Override
    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    public int getPlayerExperience() {
        return playerExperience;
    }

    public void setPlayerExperience(int playerExperience) {
        this.playerExperience = playerExperience;
    }

    @Override
    public int getSkillPoints() {
        return skillPoints;
    }

    @Override
    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

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
    public void setActiveSkillId(SkillSlot skillSlot, String skillId) {
        activeSkills.put(skillSlot, skillId);
    }

    @Override
    public void clearAll() {
        category = "";
        clearSkills();
    }

    @Override
    public void clearSkills() {
        skillPoints = playerLevel;
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

    public void syncCapability(ServerPlayer serverPlayer) {
        Messenger.sendToPlayer(new SyncSkillCapS2CPacket(this), serverPlayer);
    }

    public void copyFrom(SkillCap source) {
        this.skillMap = source.skillMap;
        this.activeSkills = source.activeSkills;
        this.playerLevel = source.playerLevel;
        this.skillPoints = source.skillPoints;
        this.category = source.category;
    }

    public void saveNBTData(CompoundTag nbt) {
        CompoundTag skillDataTag = new CompoundTag();
        for (Map.Entry<String, Skill> entry : skillMap.entrySet()) {
            String skillId = entry.getKey();
            Skill skillInstance = entry.getValue();
            skillDataTag.put(skillId, skillInstance.saveNbt());
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
        nbt.putInt("player_level", playerLevel);
        nbt.putInt("player_experience", playerExperience);
        nbt.putInt("skill_points", skillPoints);
        nbt.putString("category", category);
    }

    public void loadNBTData(CompoundTag nbt) {
        skillMap.clear();
        activeSkills.clear();
        CompoundTag skillDataTag = nbt.getCompound("skill_data");
        for (String skillId : skillDataTag.getAllKeys()) {
            if (PlayerSkillManager.skillExists(skillId)) {
                Skill skillInstance = SkillRegistry.getSkillData(skillId).getSkillInstance();
                skillInstance.loadNbt(skillDataTag.getCompound(skillId));
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

        playerLevel = nbt.getInt("player_level");
        playerExperience = nbt.getInt("player_experience");
        skillPoints = nbt.getInt("skill_points");
        category = nbt.getString("category");
    }

    public void saveClientNBTData(CompoundTag nbt) {
        CompoundTag skillLevelTag = new CompoundTag();
        for (Map.Entry<String, Skill> entry : skillMap.entrySet()) {
            String skillId = entry.getKey();
            int level = entry.getValue().getLevel();
            skillLevelTag.putInt(skillId, level);
        }

        CompoundTag activeSkillDataTag = new CompoundTag();
        for (Map.Entry<SkillSlot, String> entry : activeSkills.entrySet()) {
            CompoundTag data = new CompoundTag();
            int index = entry.getKey().getIndex();
            String skillId = entry.getValue();
            if (entry.getValue() != null) {
                ActiveSkill activeSkill = (ActiveSkill) getSkillInstance(skillId);
                data.putString("id", skillId);
                data.putInt("curr_tick", activeSkill.getCurrTick());
                data.putInt("max_tick", activeSkill.getMaxTick());
                activeSkillDataTag.put(Integer.toString(index), data);
            }
        }

        nbt.put("skill_level", skillLevelTag);
        nbt.put("active_skill_data", activeSkillDataTag);
        nbt.putInt("player_level", playerLevel);
        nbt.putInt("skill_points", skillPoints);
        nbt.putString("category", category);
    }
}
