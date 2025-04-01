package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class ParryData extends SkillData {
    public ParryData() {
        super(
                "parry",
                ParrySkill.MAX_LEVEL,
                ParrySkill::new,

                "Parry",
                (level) -> "Blocking an enemy's attack with a shield knocks them back and slows them.",
                new ResourceLocation(Skilled.MODID, "textures/skill/parry.png")
        );
    }
}
