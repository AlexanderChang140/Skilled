package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class VolleyData extends ActiveSkillData {
    public VolleyData() {
        super(
                "volley",
                1,
                VolleySkill::new,
                "Volley",
                "Increase your bow recharge rate by 100%",
                Utilities.getDefaultSkillIcon(),
                SkillSlot.TERTIARY
        );
    }
}
