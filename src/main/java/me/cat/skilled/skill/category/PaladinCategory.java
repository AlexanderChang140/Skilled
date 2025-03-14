package me.cat.skilled.skill.category;

import me.cat.skilled.Skilled;
import net.minecraft.resources.ResourceLocation;

public class PaladinCategory extends Category {
    public PaladinCategory() {
        super(
                "paladin",
                new ResourceLocation(Skilled.MODID, "textures/category/paladin.png"),
                "Paladin",
                "Paladin"
        );
    }
}
