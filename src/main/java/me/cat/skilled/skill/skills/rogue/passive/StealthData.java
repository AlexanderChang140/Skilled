package me.cat.skilled.skill.skills.rogue.passive;

import me.cat.skilled.effect.StealthEffect;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

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
                Utilities.getSkillIconWithDefaultPath("stealth.png")
        );
    }
}