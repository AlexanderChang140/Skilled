package me.cat.skilled.skill.category;

import me.cat.skilled.Skilled;
import net.minecraft.resources.ResourceLocation;

public class WarriorCategory extends Category{
    public WarriorCategory() {
        super(
                "warrior",
                new ResourceLocation(Skilled.MODID, "textures/gui"),
                "Warrior",
                "N/A"
        );
    }
}
