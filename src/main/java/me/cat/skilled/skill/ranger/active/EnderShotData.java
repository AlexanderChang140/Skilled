package me.cat.skilled.skill.ranger.active;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.skill.ActiveSkillData;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class EnderShotData extends ActiveSkillData {
    public EnderShotData() {
        super(
                "ender_shot",
                CategoryRegistry.RANGER.getId(),
                EnderShotSkill.MAX_LEVEL,
                EnderShotSkill::new,
                "Ender Shot",
                (level) -> "Teleport to the position of your next shot.",
                new ResourceLocation(Skilled.MODID, "textures/mob_effect/ender_shot.png"),
                Grid.lineX(3),
                Grid.tierY(2),
                SkillSlot.SECONDARY
        );
    }

    @Override
    public void registerPrerequisites() {
        addPrerequisite(SkillRegistry.FLEETFOOTED);
    }
}
