package me.cat.skilled.skill.skills.rogue.active;

import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class VoidwalkerData extends ActiveSkillData {

    public VoidwalkerData() {
        super(
                "voidwalker",
                1,
                VoidwalkerSkill::new,
                "Voidwalker",
                (level) -> "temp",
                Utilities.getDefaultSkillIcon(),
                SkillSlot.TERTIARY
        );
    }
}
