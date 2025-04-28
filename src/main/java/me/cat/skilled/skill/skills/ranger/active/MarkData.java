package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.effect.MarkedEffect;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class MarkData extends ActiveSkillData {
    public MarkData() {
        super(
                "mark",
                1,
                MarkSkill::new,

                "Mark",
                (level) -> String.format("Inflict §lMarked§r on an enemy, causing them to take %.0f%% more damage.", toPercent(MarkedEffect.DAMAGE_MULTIPLIER)),
                Utilities.getSkillIconWithDefaultPath("mark.png"),
                SkillSlot.PRIMARY
        );
    }
}
