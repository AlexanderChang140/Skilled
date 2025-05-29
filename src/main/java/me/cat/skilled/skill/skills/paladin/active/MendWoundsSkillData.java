package me.cat.skilled.skill.skills.paladin.active;

import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class MendWoundsSkillData extends ActiveSkillData {
    public MendWoundsSkillData() {
        super(
                "mend_wounds",
                1,
                MendWoundsSkill::new,
                "Mend Wounds",
                "Heal all allies within x radius",
                Utilities.getDefaultSkillIcon(),
                SkillSlot.SECONDARY
        );
    }
}
