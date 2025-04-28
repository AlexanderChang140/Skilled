package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class DashData extends ActiveSkillData {
    public DashData() {
        super(
                "dash",
                1,
                DashSkill::new,

                "Dash",
                (level) -> "Dash forward, knocking back and damaging enemies in your path.",
                Utilities.getSkillIconWithDefaultPath("dash.png"),
                SkillSlot.SECONDARY
        );
    }
}
