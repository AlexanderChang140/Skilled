package me.cat.skilled.skill;

public class SkillWrapper {
    private int skillLevel;
    private final Skill skillInstance;

    public SkillWrapper(int skillLevel, Skill skillInstance) {
        this.skillLevel = skillLevel;
        this.skillInstance = skillInstance;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Skill getSkillInstance() {
        return skillInstance;
    }
}
