package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class EnderShotData extends ActiveSkillData {
    public EnderShotData() {
        super(
                "ender_shot",
                1,
                EnderShotSkill::new,

                "Ender Shot",
                (level) -> "Teleport to the position of your next shot.",
                Utilities.getSkillIconWithDefaultPath("ender_shot.png"),
                SkillSlot.SECONDARY
        );
    }
}
