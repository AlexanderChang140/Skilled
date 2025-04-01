package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import net.minecraft.resources.ResourceLocation;

public class ImmobilizingShotData extends SkillData {
    public ImmobilizingShotData() {
        super(
                "immobilizing_shot",
                ImmobilizingShotSkill.MAX_LEVEL,
                ImmobilizingShotSkill::new,

                "Immobilizing Shot",
                (level) -> "Your ranged attacks §lImmobilize§r enemies, slowing them.",
                new ResourceLocation(Skilled.MODID, "textures/skill/immobilizing_shot.png")
        );
    }
}