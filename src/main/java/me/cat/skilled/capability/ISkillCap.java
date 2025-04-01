package me.cat.skilled.capability;

import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.skill.Skill;

import java.util.Collection;
import java.util.Map;

public interface ISkillCap {

    boolean hasSkill(String skillId);

    int getSkillLevel(String skillId);

    Skill getSkillInstance(String skillId);

    void updateSkill(String skillId, int level);

    void removeSkill(String skillId);

    Collection<String> getActiveSkills();

    String getActiveSkillId(SkillSlot skillSlot);


    void clearSkills();

    Map<String, Skill> getSkillMap();

    Map<SkillSlot, String> getActiveSkillMap();
}
