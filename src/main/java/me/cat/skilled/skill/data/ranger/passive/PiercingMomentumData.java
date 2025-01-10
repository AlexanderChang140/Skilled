package me.cat.skilled.skill.data.ranger.passive;

import me.cat.skilled.Skilled;
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
                (level) -> "Consecutive arrow hits on an enemy increase your ranged damage. Stacks are lost on a missed shot.",
                new ResourceLocation(Skilled.MODID, "textures/skill/piercing_momentum.png"),
                Grid.lineX(0),
                Grid.tierY(1),
                SIZE_SMALL
        );
    }
}