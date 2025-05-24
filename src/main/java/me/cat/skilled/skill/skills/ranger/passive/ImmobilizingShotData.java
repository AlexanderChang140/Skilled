package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class ImmobilizingShotData extends SkillData {
    public ImmobilizingShotData() {
        super(
                "immobilizing_shot",
                1,
                ImmobilizingShotSkill::new,

                "Immobilizing Shot",
                (level) -> "Your ranged attacks §lImmobilize§r enemies, slowing them.",
                Utilities.getDefaultSkillIcon()
        );
    }
}