package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class BloodlustData extends SkillData {
    public BloodlustData() {
        super(
                "bloodlust",
                1,
                BloodlustSkill::new,
                "Bloodlust",
                "Gain increased damage on kill",
                Utilities.getDefaultSkillIcon()
        );
    }
}
