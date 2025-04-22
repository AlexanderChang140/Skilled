package me.cat.skilled.util;

import me.cat.skilled.Skilled;
import net.minecraft.resources.ResourceLocation;

public class Utilities {
    private static final String DEFAULT_ICON_PATH = "textures/skill/";

    public static ResourceLocation getDefaultSkillIconPath(String fileName) {
        return new ResourceLocation(Skilled.MODID, DEFAULT_ICON_PATH + fileName);
    }
}
