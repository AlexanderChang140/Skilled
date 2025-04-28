package me.cat.skilled.skill.skills.paladin.active;

import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class DivineSmiteData extends ActiveSkillData {
    public DivineSmiteData() {
        super(
                "divine_smite",
                1,
                DivineSmiteSkill::new,

                "Divine Smite",
                (level) -> "Deal extra damage",
                Utilities.getSkillIconWithDefaultPath("divine_smite.png"),
                SkillSlot.SECONDARY
        );
    }
}
