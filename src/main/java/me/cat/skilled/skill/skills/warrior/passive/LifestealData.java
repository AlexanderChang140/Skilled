package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class LifestealData extends SkillData {
    public LifestealData() {
        super(
                "lifesteal",
                1,
                LifestealSkill::new,

                "Lifesteal",
                (level) -> String.format(
                        "Heal %.0f%% of the melee damage you deal.",
                        toPercent(LifestealSkill.getHealPercent())
                ),
                Utilities.getDefaultSkillIcon()
        );
    }
}
