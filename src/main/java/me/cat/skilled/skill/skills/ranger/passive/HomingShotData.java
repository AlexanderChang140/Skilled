package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class HomingShotData extends SkillData {
    public HomingShotData() {
        super(
                "homing_shot",
                HomingShotSkill.MAX_LEVEL,
                HomingShotSkill::new,

                "Homing Shot",
                (level) -> "Your projectiles home in on §lMarked§l enemies.",
                new ResourceLocation(Skilled.MODID, "textures/skill/homing_shot.png")
        );
    }
}
