package me.cat.skilled.skill.data.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.ranger.passive.PowerShotSkill;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class PowerShotData extends SkillData {
    public PowerShotData() {
        super(
                "power_shot",
                CategoryRegistry.RANGER.getId(),
                PowerShotSkill.MAX_LEVEL,
                PowerShotSkill::new,
                0,

                "Power Shot",
                (level) -> String.format("Your projectiles travel %.0f%% faster.",
                        toPercentOffset(PowerShotSkill.getVelocityMultiplier(level))
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/power_shot.png"),
                Grid.lineX(2),
                Grid.tierY(1),
                SIZE_LARGE
        );
    }
}