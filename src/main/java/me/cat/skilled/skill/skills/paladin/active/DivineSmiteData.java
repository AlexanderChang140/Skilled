package me.cat.skilled.skill.skills.paladin.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.skill.SkillSlot;
import net.minecraft.resources.ResourceLocation;

public class DivineSmiteData extends ActiveSkillData {
    public DivineSmiteData() {
        super(
                "divine_smite",
                1,
                DivineSmiteSkill::new,

                "Divine Smite",
                (level) -> "Deal extra damage",
                new ResourceLocation(Skilled.MODID, "textures/skill/divine_smite.png"),
                SkillSlot.SECONDARY
        );
    }
}
