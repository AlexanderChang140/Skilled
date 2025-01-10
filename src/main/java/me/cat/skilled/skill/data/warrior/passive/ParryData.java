package me.cat.skilled.skill.data.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.warrior.passive.ParrySkill;
import net.minecraft.resources.ResourceLocation;

public class ParryData extends SkillData {
    public ParryData() {
        super(
                "parry",
                CategoryRegistry.WARRIOR.getId(),
                ParrySkill.MAX_LEVEL,
                ParrySkill::new,
                "Parry",
                (level) -> "Blocking an enemy's attack with a shield knocks them back and slows them",
                new ResourceLocation(Skilled.MODID, "textures/skill/parry.png"),
                10,
                10
        );
    }
}
