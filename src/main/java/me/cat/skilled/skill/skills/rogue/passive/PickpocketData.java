package me.cat.skilled.skill.skills.rogue.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class PickpocketData extends SkillData {
    public PickpocketData() {
        super(
                "pickpocket",
                1,
                PickpocketSkill::new,
                "Pickpocket",
                "You gain one extra level of looting.",
                Utilities.getDefaultSkillIcon()
        );
    }
}
