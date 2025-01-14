package me.cat.skilled.skill.data.ranger.passive;

import me.cat.skilled.Skilled;;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.ranger.passive.ShrapnelBarrageSkill;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class ShrapnelBarrageData extends SkillData {
    public ShrapnelBarrageData() {
        super(
                "shrapnel_barrage",
                CategoryRegistry.RANGER.getId(),
                ShrapnelBarrageSkill.MAX_LEVEL,
                ShrapnelBarrageSkill::new,
                0,

                "Shrapnel Barrage",
                (level) -> String.format(
                        "Inflict %.0f%% of the damage dealt to nearby enemies when striking a §lMarked§r target with a ranged attack.",
                        toPercent(ShrapnelBarrageSkill.getDamageMultiplier(level))
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/shrapnel_barrage.png"),
                Grid.lineX(1),
                Grid.tierY(3),
                SIZE_LARGE
        );
    }
}
