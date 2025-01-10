package me.cat.skilled.skill.data.warrior.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.data.ActiveSkillData;
import me.cat.skilled.skill.instance.warrior.active.SlashSkill;
import net.minecraft.resources.ResourceLocation;

public class SlashData extends ActiveSkillData {

    public SlashData() {
        super(
                "slash",
                CategoryRegistry.WARRIOR.getId(),
                SlashSkill.MAX_LEVEL,
                SlashSkill::new,
                "Slash",
                (level) -> "Slash in front of you, dealing x damage",
                new ResourceLocation(Skilled.MODID, "textures/gui/skill/slash.png"),
                10,
                10,
                SkillSlot.PRIMARY
        );
    }
}
