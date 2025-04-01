package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class LifestealData extends SkillData {
    public LifestealData() {
        super(
                "lifesteal",
                LifestealSkill.MAX_LEVEL,
                LifestealSkill::new,

                "Lifesteal",
                (level) -> String.format(
                        "Heal %.0f%% of the melee damage you deal.",
                        toPercent(LifestealSkill.getHealPercent())
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/life_steal.png")
        );
    }
}
