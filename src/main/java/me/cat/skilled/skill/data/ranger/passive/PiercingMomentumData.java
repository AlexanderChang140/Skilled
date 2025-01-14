package me.cat.skilled.skill.data.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.effect.PiercingMomentumEffect;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.ranger.passive.PiercingMomentumSkill;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;


public class PiercingMomentumData extends SkillData {
    public PiercingMomentumData() {
        super(
                "piercing_momentum",
                CategoryRegistry.RANGER.getId(),
                PiercingMomentumSkill.MAX_LEVEL,
                PiercingMomentumSkill::new,
                0,

                "Piercing Momentum",
                (level) ->  String.format(
                        "Consecutive ranged attacks on an enemy increase your ranged damage by %.0f%% per hit (max %d stacks). Stacks are lost on a missed attack.",
                        PiercingMomentumEffect.DAMAGE_MULTIPLIER,
                        PiercingMomentumSkill.getMaxStacks()
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/piercing_momentum.png"),
                Grid.lineX(1),
                Grid.tierY(1),
                SIZE_SMALL
        );
    }
}