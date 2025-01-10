package me.cat.skilled.skill.category;

import me.cat.skilled.Skilled;
import net.minecraft.resources.ResourceLocation;

public class RangerCategory extends Category {
    public RangerCategory() {
        super(
                "ranger",
                new ResourceLocation(Skilled.MODID, "textures/gui"),
                "Ranger",
                "N/A"
        );
    }
}
