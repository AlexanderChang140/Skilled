package me.cat.skilled.skill.data.warrior.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.ranger.active.EnderShotSkill;
import me.cat.skilled.skill.instance.ranger.active.MarkSkill;
import net.minecraft.resources.ResourceLocation;

public class LastStandData extends SkillData {
    public LastStandData() {
        super(
                "last_stand",
                CategoryRegistry.WARRIOR.getId(),
                MarkSkill.MAX_LEVEL,
                EnderShotSkill::new,
                "Last Stand",
                (level) -> "Upon taking lethal damage, gain invulnerability for x% seconds and...",
                new ResourceLocation(Skilled.MODID, "textures/skill/last_stand.png"),
                10,
                10
        );
    }
}
