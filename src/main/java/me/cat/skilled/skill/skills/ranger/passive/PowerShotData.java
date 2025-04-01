package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

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
                new ResourceLocation(Skilled.MODID, "textures/skill/power_shot.png")
        );
    }
}