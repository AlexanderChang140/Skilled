package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.skill.ActiveSkillData;
import net.minecraft.resources.ResourceLocation;

public class EnderShotData extends ActiveSkillData {
    public EnderShotData() {
        super(
                "ender_shot",
                1,
                EnderShotSkill::new,

                "Ender Shot",
                (level) -> "Teleport to the position of your next shot.",
                new ResourceLocation(Skilled.MODID, "textures/mob_effect/ender_shot.png"),
                SkillSlot.SECONDARY
        );
    }
}
