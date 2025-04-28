package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class PowerShotData extends SkillData {
    public PowerShotData() {
        super(
                "power_shot",
                PowerShotSkill.MAX_LEVEL,
                PowerShotSkill::new,

                "Power Shot",
                (level) -> String.format("Your projectiles travel %.0f%% faster.",
                        toPercentOffset(PowerShotSkill.getVelocityMultiplier(level))
                ),
                Utilities.getSkillIconWithDefaultPath("power_shot.png")
        );
    }
}