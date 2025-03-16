package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class SecondWindData extends ActiveSkillData {
    public SecondWindData() {
        super(
                "second_wind",
                CategoryRegistry.WARRIOR.getId(),
                1,
                SecondWindSkill::new,
                0,

                "Second Wind",
                (level) -> String.format(
                        "Heal %.0f%% percent of your missing health.",
                        SecondWindSkill.HEAL_FACTOR
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/second_wind.png"),
                Grid.lineX(2),
                Grid.tierY(2),
                SkillSlot.TERTIARY
        );
    }
}
