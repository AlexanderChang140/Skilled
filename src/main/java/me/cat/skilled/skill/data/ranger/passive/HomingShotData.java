package me.cat.skilled.skill.data.ranger.passive;

import me.cat.skilled.Skilled;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.ranger.passive.HomingShotSkill;
import me.cat.skilled.util.Grid;
import net.minecraft.resources.ResourceLocation;

public class HomingShotData extends SkillData {
    public HomingShotData() {
        super(
                "homing_shot",
                CategoryRegistry.RANGER.getId(),
                HomingShotSkill.MAX_LEVEL,
                HomingShotSkill::new,

                "Homing Shot",
                (level) -> "Your projectiles home in on §lMarked§l enemies.",
                new ResourceLocation(Skilled.MODID, "textures/skill/homing_shot.png"),
                Grid.lineX(1) + Grid.getPos(-1),
                Grid.tierY(2)
        );
    }

    @Override
    public void registerPrerequisites() {
        addPrerequisite(SkillRegistry.MARK);
    }
}
