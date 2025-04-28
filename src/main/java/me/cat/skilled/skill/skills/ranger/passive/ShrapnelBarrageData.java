package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class ShrapnelBarrageData extends SkillData {
    public ShrapnelBarrageData() {
        super(
                "shrapnel_barrage",
                1,
                ShrapnelBarrageSkill::new,

                "Shrapnel Barrage",
                (level) -> String.format(
                        "Inflict %.0f%% of the damage dealt to nearby enemies when striking a §lMarked§r target with a ranged attack.",
                        toPercent(ShrapnelBarrageSkill.getDamageMultiplier(level))
                ),
                Utilities.getSkillIconWithDefaultPath("shrapnel_barrage.png")
        );
    }
}
