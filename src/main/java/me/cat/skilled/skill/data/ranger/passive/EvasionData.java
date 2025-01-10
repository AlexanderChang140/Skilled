package me.cat.skilled.skill.data.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.ranger.passive.EvasionSkill;
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
                (level) -> "You have a x%% chance to evade attacks",
                new ResourceLocation(Skilled.MODID, "textures/skill/power_shot.png"),
                Grid.lineX(3),
                Grid.tierY(3),
                SIZE_LARGE
        );
    }

    @Override
    public void registerPrerequisites() {
        addPrerequisite(SkillRegistry.ENDER_SHOT);
    }
}