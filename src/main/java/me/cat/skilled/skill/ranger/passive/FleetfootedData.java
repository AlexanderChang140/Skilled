package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class FleetfootedData extends SkillData {
    public FleetfootedData() {
        super(
                "fleetfooted",
                CategoryRegistry.RANGER.getId(),
                FleetfootedSkill.MAX_LEVEL,
                FleetfootedSkill::new,
                0,

                "Fleetfooted",
                (level) -> String.format(
                        "You move at %.0f%% speed when crouching or using a ranged weapon.",
                        toPercent(FleetfootedSkill.getCrouchMovementSpeed(level))
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/fleetfooted.png"),
                Grid.lineX(3),
                Grid.tierY(1),
                SIZE
        );
    }
}