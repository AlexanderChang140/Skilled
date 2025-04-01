package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.skill.ActiveSkillData;
import net.minecraft.resources.ResourceLocation;

public class DashData extends ActiveSkillData {
    public DashData() {
        super(
                "dash",
                1,
                DashSkill::new,

                "Dash",
                (level) -> "Dash forward, knocking back and damaging enemies in your path.",
                new ResourceLocation(Skilled.MODID, "textures/skill/dash.png"),
                SkillSlot.SECONDARY
        );
    }
}
