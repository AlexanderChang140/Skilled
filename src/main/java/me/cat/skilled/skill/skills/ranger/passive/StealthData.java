package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.effect.StealthEffect;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class StealthData extends SkillData {
    public StealthData() {
        super(
                "stealth",
                CategoryRegistry.RANGER.getId(),
                StealthSkill.MAX_LEVEL,
                StealthSkill::new,

                "Stealth",
                (level) -> String.format(
                        "Your detection range is reduced by %.0f%% while crouching. Attacking breaks stealth.",
                        toPercent(level * StealthEffect.DETECTION_DECREASE)
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/stealth.png"),
                Grid.lineX(3) + Grid.getPos(1),
                Grid.tierY(1)
        );
    }

    @Override
    public void registerPrerequisites() {
        addPrerequisite(SkillRegistry.FLEETFOOTED);
    }
}