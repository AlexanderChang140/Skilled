package me.cat.skilled.skill.skills.rogue.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class FleetfootedData extends SkillData {
    public FleetfootedData() {
        super(
                "fleetfooted",
                1,
                FleetfootedSkill::new,

                "Fleetfooted",
                (level) -> String.format(
                        "You move at %.0f%% speed when crouching or using a ranged weapon.",
                        toPercent(FleetfootedSkill.getCrouchMovementSpeed(level))
                ),
                Utilities.getSkillIconWithDefaultPath("fleetfooted.png")
        );
    }
}