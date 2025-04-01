package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.effect.PiercingMomentumEffect;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;


public class PiercingMomentumData extends SkillData {
    public PiercingMomentumData() {
        super(
                "piercing_momentum",
                PiercingMomentumSkill.MAX_LEVEL,
                PiercingMomentumSkill::new,

                "Piercing Momentum",
                (level) ->  String.format(
                        "Consecutive ranged attacks on an enemy increase your ranged damage by %.0f%% per hit (max %d stacks). Stacks are lost on a missed attack.",
                        PiercingMomentumEffect.DAMAGE_MULTIPLIER,
                        PiercingMomentumSkill.getMaxStacks()
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/piercing_momentum.png")
        );
    }
}