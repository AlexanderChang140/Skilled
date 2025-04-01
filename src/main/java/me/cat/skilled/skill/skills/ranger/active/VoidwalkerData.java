package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import net.minecraft.resources.ResourceLocation;

public class VoidwalkerData extends ActiveSkillData {

    public VoidwalkerData() {
        super(
                "voidwalker",
                1,
                VoidwalkerSkill::new,
                "Voidwalker",
                (level) -> "temp",
                new ResourceLocation(Skilled.MODID, "textures/skill/voidwalker.png"),
                SkillSlot.TERTIARY
        );
    }
}
