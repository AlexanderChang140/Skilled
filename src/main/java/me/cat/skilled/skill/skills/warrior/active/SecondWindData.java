package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class SecondWindData extends ActiveSkillData {
    public SecondWindData() {
        super(
                "second_wind",
                1,
                SecondWindSkill::new,

                "Second Wind",
                (level) -> String.format(
                        "Heal %.0f%% percent of your missing health.",
                        SecondWindSkill.HEAL_FACTOR
                ),
                Utilities.getSkillIconWithDefaultPath("second_wind.png"),
                SkillSlot.TERTIARY
        );
    }
}
