package me.cat.skilled.skill.skills.rogue.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.effect.StealthEffect;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class StealthData extends SkillData {
    public StealthData() {
        super(
                "stealth",
                StealthSkill.MAX_LEVEL,
                StealthSkill::new,

                "Stealth",
                (level) -> String.format(
                        "Your detection range is reduced by %.0f%% while crouching. Attacking breaks stealth.",
                        toPercent(level * StealthEffect.DETECTION_DECREASE)
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/stealth.png")
        );
    }
}