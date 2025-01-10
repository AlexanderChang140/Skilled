package me.cat.skilled.skill.data.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.warrior.passive.FrenzySkill;
import net.minecraft.resources.ResourceLocation;

public class FrenzyData extends SkillData {
    public FrenzyData() {
        super(
                "frenzy",
                CategoryRegistry.WARRIOR.getId(),
                FrenzySkill.MAX_LEVEL,
                FrenzySkill::new,
                "Frenzy",
                (level) -> "Your attack speed increases by x% each time you attack an enemy",
                new ResourceLocation(Skilled.MODID, "textures/skill/frenzy.png"),
                10,
                10
        );
    }
}
