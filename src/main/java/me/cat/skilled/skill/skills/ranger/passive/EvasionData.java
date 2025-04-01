package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class EvasionData extends SkillData {
    public EvasionData() {
        super(
                "evasion",
                EvasionSkill.MAX_LEVEL,
                EvasionSkill::new,

                "Evasion",
                (level) -> String.format(
                        "You have a %.0f%% chance to evade attacks. This chance is reduced every hit evaded and recharges over time.",
                        EvasionSkill.getMaxEvasion(level)
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/power_shot.png")
        );
    }
}