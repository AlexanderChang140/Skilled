package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class RepositionData extends SkillData {
    public RepositionData() {
        super(
                "reposition",
                1,
                RepositionSkill::new,
                "Reposition",
                "Gain increased movement speed after firing an arrow.",
                Utilities.getDefaultSkillIcon()
        );
    }
}
