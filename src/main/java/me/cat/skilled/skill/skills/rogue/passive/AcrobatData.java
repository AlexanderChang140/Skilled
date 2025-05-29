package me.cat.skilled.skill.skills.rogue.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class AcrobatData extends SkillData {
    public AcrobatData() {
        super(
                "acrobat",
                1,
                AcrobatSkill::new,
                "Acrobat",
                "Fall damage is reduced by 50%",
                Utilities.getDefaultSkillIcon()
        );
    }
}
