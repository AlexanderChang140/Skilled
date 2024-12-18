package me.cat.skilled.capability;

import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.SyncCapabilityS2CPacket;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.skill.SkillFactory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.HashMap;
import java.util.Map;

@AutoRegisterCapability
public class PlayerSkills {

    private Map<String, Skill> skillMap = new HashMap<>();
    private String primarySkillId = "";

    public boolean hasSkill(String skillId) {
        return skillMap.containsKey(skillId);
    }

    public int getSkillLevel(String skillId) {
        return skillMap.get(skillId).getLevel();
    }

    public Skill getSkillInstance(String skillId) {
        if (skillMap.containsKey(skillId)) {
            return skillMap.get(skillId);
        }
        return null;
    }

    public void updateSkill(String skillId, int level) {
        if (level == 0) {
            skillMap.remove(skillId);
        }
        else {
            skillMap.compute(skillId, (id, skill) -> {
                if (skill == null) {
                    skill = SkillFactory.getSkill(id);
                }
                if (skill != null) {
                    skill.setLevel(level);
                }
                return skill;
            });
        }

        if (skillMap.containsKey(skillId)) {
            if (level == 0) {
                skillMap.remove(skillId);
            }
            else {
                skillMap.get(skillId).setLevel(level);
            }
        }
        else {
            if (level != 0) {
                Skill skillInstance = SkillFactory.getSkill(skillId);
                if (skillInstance != null) {
                    skillInstance.setLevel(level);
                    skillMap.put(skillId, skillInstance);
                }
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
        Skill skillInstance = skillMap.get(skillId);
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

    public Map<String, Skill> getMap() {
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
        CompoundTag skillDataTag = new CompoundTag();
        for (Map.Entry<String, Skill> entry : skillMap.entrySet()) {
            String skillId = entry.getKey();
            Skill skillInstance = entry.getValue();

            skillDataTag.put(skillId, skillInstance.saveNbt());
        }
        nbt.put("skill_data", skillDataTag);
        nbt.putString("primary_skill_id", primarySkillId);
    }

    public void loadNBTData(CompoundTag nbt) {
        skillMap.clear();
        CompoundTag skillDataTag = nbt.getCompound("skill_data");
        for (String skillId : skillDataTag.getAllKeys()) {
            Skill skillInstance = SkillFactory.getSkill(skillId);
            skillInstance.loadNbt(skillDataTag.getCompound(skillId));

            skillMap.put(skillId, skillInstance);
        }
        primarySkillId = nbt.getString("primary_skill_id");
    }
}
