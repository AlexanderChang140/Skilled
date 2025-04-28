package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Utilities;

public class SlashData extends ActiveSkillData {

    public SlashData() {
        super(
                "slash",
                1,
                SlashSkill::new,

                "Slash",
                (level) -> String.format(
                        "Slash in front of you, dealing %.0f%% melee damage in an area.",
                        toPercent(SlashSkill.getDamageMultiplier())
                ),
                Utilities.getSkillIconWithDefaultPath("slash.png"),
                SkillSlot.PRIMARY
        );
    }
}
