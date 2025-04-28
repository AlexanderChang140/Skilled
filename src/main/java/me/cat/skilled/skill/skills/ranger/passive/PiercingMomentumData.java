package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.effect.PiercingMomentumEffect;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;


public class PiercingMomentumData extends SkillData {
    public PiercingMomentumData() {
        super(
                "piercing_momentum",
                PiercingMomentumSkill.MAX_LEVEL,
                PiercingMomentumSkill::new,

                "Piercing Momentum",
                (level) ->  String.format(
                        "Consecutive ranged attacks on an enemy increase your ranged damage by %.0f%% per hit (max %d stacks). Stacks are lost on a missed attack.",
                        PiercingMomentumEffect.DAMAGE_MULTIPLIER,
                        PiercingMomentumSkill.getMaxStacks()
                ),
                Utilities.getSkillIconWithDefaultPath("piercing_momentum.png")
        );
    }
}