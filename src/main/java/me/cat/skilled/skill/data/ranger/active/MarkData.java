package me.cat.skilled.skill.data.ranger.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.data.ActiveSkillData;
import me.cat.skilled.skill.instance.ranger.active.MarkSkill;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class MarkData extends ActiveSkillData {
    public MarkData() {
        super(
                "mark",
                CategoryRegistry.RANGER.getId(),
                MarkSkill.MAX_LEVEL,
                MarkSkill::new,

                "Mark",
                (level) -> "Mark an enemy. Marked enemies take 25% more damage",
                new ResourceLocation(Skilled.MODID, "textures/skill/mark.png"),
                Grid.lineX(1),
                Grid.tierY(2),
                SkillSlot.SECONDARY
        );
    }
}
