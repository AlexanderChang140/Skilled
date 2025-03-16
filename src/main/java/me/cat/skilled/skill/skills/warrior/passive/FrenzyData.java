package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.effect.FrenzyEffect;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class FrenzyData extends SkillData {
    public FrenzyData() {
        super(
                "frenzy",
                CategoryRegistry.RANGER.getId(),
                FrenzySkill.MAX_LEVEL,
                FrenzySkill::new,

                "Frenzy",
                (level) -> String.format(
                        "Your attack speed increases by %.0f%% with each melee attack on an enemy.",
                        toPercent(FrenzyEffect.FRENZY_ATTACK_SPEED_INCREASE)
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/frenzy.png"),
                Grid.lineX(2),
                Grid.tierY(1)
        );
    }
}
