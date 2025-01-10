package me.cat.skilled.capability;

import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.instance.Skill;

import java.util.Collection;
import java.util.Map;

public interface ISkillCap {
    int getPlayerLevel();

    void setPlayerLevel(int playerLevel);

    int getSkillPoints();

    void setSkillPoints(int skillPoints);

    String getCategory();

    void setCategory(String category);

    boolean hasSkill(String skillId);

    int getSkillLevel(String skillId);

    Skill getSkillInstance(String skillId);

    void updateSkill(String skillId, int level);

    void removeSkill(String skillId);

    Collection<String> getActiveSkills();

    String getActiveSkillId(SkillSlot skillSlot);

    void setActiveSkillId(SkillSlot skillSlot, String skillId);

    void clearAll();

    void clearSkills();

    Map<String, Skill> getSkillMap();

    Map<SkillSlot, String> getActiveSkillMap();
}
