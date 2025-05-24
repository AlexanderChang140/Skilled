package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class ParryData extends SkillData {
    public ParryData() {
        super(
                "parry",
                1,
                ParrySkill::new,

                "Parry",
                (level) -> "Blocking an enemy's attack with a shield knocks them back and slows them.",
                Utilities.getDefaultSkillIcon()
        );
    }
}
