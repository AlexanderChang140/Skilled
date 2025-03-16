package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import net.minecraft.resources.ResourceLocation;

public class VoidwalkerData extends ActiveSkillData {

    public VoidwalkerData() {
        super(
                "voidwalker",
                CategoryRegistry.RANGER.getId(),
                1,
                VoidwalkerSkill::new,
                0,

                "Voidwalker",
                (level) -> "temp",
                new ResourceLocation(Skilled.MODID, "textures/skill/voidwalker.png"),
                0,
                0,
                SkillSlot.TERTIARY);
    }
}
