package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class LastStandData extends SkillData {
    public LastStandData() {
        super(
                "last_stand",
                LastStandSkill.MAX_LEVEL,
                LastStandSkill::new,

                "Last Stand",
                (level) -> String.format(
                        "Upon taking lethal damage become invulnerable for %d seconds.",
                        LastStandSkill.getInvulnDuration() / 20
                ),
                new ResourceLocation(Skilled.MODID, "textures/skill/last_stand.png")
        );
    }
}
