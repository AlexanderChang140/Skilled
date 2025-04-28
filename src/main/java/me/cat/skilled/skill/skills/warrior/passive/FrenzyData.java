package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.effect.FrenzyEffect;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class FrenzyData extends SkillData {
    public FrenzyData() {
        super(
                "frenzy",
                FrenzySkill.MAX_LEVEL,
                FrenzySkill::new,

                "Frenzy",
                (level) -> String.format(
                        "Your attack speed increases by %.0f%% with each melee attack on an enemy.",
                        toPercent(FrenzyEffect.FRENZY_ATTACK_SPEED_INCREASE)
                ),
                Utilities.getDefaultSkillIcon()
        );
    }
}
