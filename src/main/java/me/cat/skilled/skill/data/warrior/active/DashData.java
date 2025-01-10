package me.cat.skilled.skill.data.warrior.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.data.ActiveSkillData;
import me.cat.skilled.skill.instance.warrior.active.DashSkill;
import net.minecraft.resources.ResourceLocation;

public class DashData extends ActiveSkillData {
    public DashData() {
        super(
                "dash",
                CategoryRegistry.WARRIOR.getId(),
                DashSkill.MAX_LEVEL,
                DashSkill::new,
                "Dash",
                (level) -> "Dash forward, knocking back and damaging enemies in your path",
                new ResourceLocation(Skilled.MODID, "textures/skill/dash.png"),
                10,
                10,
                SkillSlot.PRIMARY
        );
    }
}
