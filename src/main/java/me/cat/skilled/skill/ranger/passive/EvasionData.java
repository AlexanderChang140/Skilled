package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class EvasionData extends SkillData {
    public EvasionData() {
        super(
                "evasion",
                CategoryRegistry.RANGER.getId(),
                EvasionSkill.MAX_LEVEL,
                EvasionSkill::new,
                15,

                "Evasion",
                (level) -> String.format(
                        "You have a %.0f%% chance to evade attacks. This chance is reduced every hit evaded and recharges over time.",
                        EvasionSkill.getMaxEvasion(level)
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/power_shot.png"),
                Grid.lineX(3),
                Grid.tierY(3),
                DEFAULT_SIZE
        );
    }

    @Override
    public void registerPrerequisites() {
        addPrerequisite(SkillRegistry.ENDER_SHOT);
    }
}