package me.cat.skilled.skill.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class LastStandData extends SkillData {
    public LastStandData() {
        super(
                "last_stand",
                CategoryRegistry.WARRIOR.getId(),
                LastStandSkill.MAX_LEVEL,
                LastStandSkill::new,
                "Last Stand",
                (level) -> String.format(
                        "Upon taking lethal damage become invulnerable for %d seconds.",
                        LastStandSkill.getInvulnDuration() / 20
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/last_stand.png"),
                Grid.lineX(2),
                Grid.tierY(3)
        );
    }
}
