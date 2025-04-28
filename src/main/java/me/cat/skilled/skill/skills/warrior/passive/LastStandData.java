package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class LastStandData extends SkillData {
    public LastStandData() {
        super(
                "last_stand",
                LastStandSkill.MAX_LEVEL,
                LastStandSkill::new,

                "Last Stand",
                (level) -> String.format(
                        "Upon taking lethal damage become invulnerable for %d seconds.",
                        LastStandSkill.getInvulnDuration() / 20
                ),
                Utilities.getDefaultSkillIcon()
        );
    }
}
