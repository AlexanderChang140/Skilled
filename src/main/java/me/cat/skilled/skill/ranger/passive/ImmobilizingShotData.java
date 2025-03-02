package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class ImmobilizingShotData extends SkillData {
    public ImmobilizingShotData() {
        super(
                "immobilizing_shot",
                CategoryRegistry.RANGER.getId(),
                ImmobilizingShotSkill.MAX_LEVEL,
                ImmobilizingShotSkill::new,

                "Immobilizing Shot",
                (level) -> "Your ranged attacks §lImmobilize§r enemies, slowing them.",
                new ResourceLocation(Skilled.MODID, "textures/skill/immobilizing_shot.png"),
                Grid.lineX(2) + Grid.getPos(1),
                Grid.tierY(1)
        );
    }

    @Override
    public void registerPrerequisites() {
        addPrerequisite(SkillRegistry.POWER_SHOT);
    }
}