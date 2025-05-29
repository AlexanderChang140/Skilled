package me.cat.skilled.skill.skills.paladin.passive;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Utilities;

public class AuraOfProtectionData extends SkillData {
    public AuraOfProtectionData() {
        super(
                "aura_of_protection",
                1,
                AuraOfProtectionSkill::new,
                "Aura of Protection",
                "Nearby allies receive 15% damage reduction",
                Utilities.getDefaultSkillIcon()
        );
    }
}
