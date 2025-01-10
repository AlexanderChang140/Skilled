package me.cat.skilled.skill.data.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.data.ActiveSkillData;
import me.cat.skilled.skill.instance.warrior.passive.LifestealSkill;
import net.minecraft.resources.ResourceLocation;

public class LifestealData extends ActiveSkillData {
    public LifestealData() {
        super(
                "lifesteal",
                CategoryRegistry.WARRIOR.getId(),
                LifestealSkill.MAX_LEVEL,
                LifestealSkill::new,
                "Lifesteal",
                (level) -> "Heal x% of the damage you deal",
                new ResourceLocation(Skilled.MODID, "textures/skill/life_steal.png"),
                10,
                10,
                SkillSlot.PRIMARY
        );
    }
}
