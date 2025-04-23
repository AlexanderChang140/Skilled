package me.cat.skilled.skill.skills.rogue.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class FleetfootedData extends SkillData {
    public FleetfootedData() {
        super(
                "fleetfooted",
                FleetfootedSkill.MAX_LEVEL,
                FleetfootedSkill::new,

                "Fleetfooted",
                (level) -> String.format(
                        "You move at %.0f%% speed when crouching or using a ranged weapon.",
                        toPercent(FleetfootedSkill.getCrouchMovementSpeed(level))
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/fleetfooted.png")
        );
    }
}