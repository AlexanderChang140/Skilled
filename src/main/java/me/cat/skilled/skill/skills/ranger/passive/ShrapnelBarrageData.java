package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class ShrapnelBarrageData extends SkillData {
    public ShrapnelBarrageData() {
        super(
                "shrapnel_barrage",
                1,
                ShrapnelBarrageSkill::new,

                "Shrapnel Barrage",
                (level) -> String.format(
                        "Inflict %.0f%% of the damage dealt to nearby enemies when striking a §lMarked§r target with a ranged attack.",
                        toPercent(ShrapnelBarrageSkill.getDamageMultiplier(level))
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/shrapnel_barrage.png")
        );
    }
}
