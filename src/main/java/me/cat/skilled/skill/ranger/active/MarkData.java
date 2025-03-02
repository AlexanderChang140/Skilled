package me.cat.skilled.skill.ranger.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.effect.MarkedEffect;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.skill.ActiveSkillData;
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
                (level) -> String.format("Inflict §lMarked§r on an enemy, causing them to take %.0f%% more damage.", toPercent(MarkedEffect.DAMAGE_MULTIPLIER)),
                new ResourceLocation(Skilled.MODID, "textures/skill/mark.png"),
                Grid.lineX(1),
                Grid.tierY(2),
                SkillSlot.PRIMARY
        );
    }
}
