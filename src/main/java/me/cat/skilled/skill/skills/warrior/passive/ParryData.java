package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class ParryData extends SkillData {
    public ParryData() {
        super(
                "parry",
                CategoryRegistry.WARRIOR.getId(),
                ParrySkill.MAX_LEVEL,
                ParrySkill::new,

                "Parry",
                (level) -> "Blocking an enemy's attack with a shield knocks them back and slows them.",
                new ResourceLocation(Skilled.MODID, "textures/skill/parry.png"),
                Grid.lineX(3) + Grid.getPos(1),
                Grid.tierY(2)
        );
    }
}
