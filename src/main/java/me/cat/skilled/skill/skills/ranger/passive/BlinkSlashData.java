package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class BlinkSlashData extends SkillData {
    public BlinkSlashData() {
        super(
                "blink_slash",
                1,
                BlinkSlashSkill::new,

                "Blink Slash",
                (level) -> "Deal additional melee damage after teleporting",
                Utilities.getDefaultSkillIconPath("blink_slash.png"));
    }
}
