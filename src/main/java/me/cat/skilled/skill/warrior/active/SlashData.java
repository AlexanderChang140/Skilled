package me.cat.skilled.skill.warrior.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class SlashData extends ActiveSkillData {

    public SlashData() {
        super(
                "slash",
                CategoryRegistry.WARRIOR.getId(),
                SlashSkill.MAX_LEVEL,
                SlashSkill::new,
                "Slash",

                (level) -> String.format(
                        "Slash in front of you, dealing %.0f%% melee damage in an area.",
                        toPercent(SlashSkill.getDamageMultiplier(level))
                ),
                new ResourceLocation(Skilled.MODID, "textures/gui/skill/slash.png"),
                Grid.lineX(1),
                Grid.tierY(2),
                SkillSlot.PRIMARY
        );
    }
}
