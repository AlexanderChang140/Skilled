package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class TenacityData extends SkillData {
    public TenacityData() {
        super(
                "tenacity",
                1,
                TenacitySkill::new,
                "Tenacity",
                "Gain increased health",
                Utilities.getDefaultSkillIcon()
        );
    }
}
