package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import net.minecraft.resources.ResourceLocation;

public class SecondWindData extends ActiveSkillData {
    public SecondWindData() {
        super(
                "second_wind",
                1,
                SecondWindSkill::new,

                "Second Wind",
                (level) -> String.format(
                        "Heal %.0f%% percent of your missing health.",
                        SecondWindSkill.HEAL_FACTOR
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/second_wind.png"),
                SkillSlot.TERTIARY
        );
    }
}
