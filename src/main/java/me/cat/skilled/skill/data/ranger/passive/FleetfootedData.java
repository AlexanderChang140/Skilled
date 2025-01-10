package me.cat.skilled.skill.data.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.ranger.passive.FleetfootedSkill;
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
                (level) -> "You move at x% speed when crouching or drawing a bow",
                new ResourceLocation(Skilled.MODID, "textures/skill/fleetfooted.png"),
                Grid.lineX(3),
                Grid.tierY(1),
                SIZE_LARGE
        );
    }
}