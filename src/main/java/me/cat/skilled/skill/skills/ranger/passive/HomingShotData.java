package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class HomingShotData extends SkillData {
    public HomingShotData() {
        super(
                "homing_shot",
                HomingShotSkill.MAX_LEVEL,
                HomingShotSkill::new,

                "Homing Shot",
                (level) -> "Your projectiles home in on §lMarked§l enemies.",
                Utilities.getSkillIconWithDefaultPath("homing_shot.png")
        );
    }
}
