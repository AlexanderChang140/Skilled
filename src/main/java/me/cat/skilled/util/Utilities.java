package me.cat.skilled.util;

import me.cat.skilled.Skilled;
import net.minecraft.resources.ResourceLocation;

public class Utilities {
    private static final String DEFAULT_SKILL_ICON_PATH = "textures/skill/";
    private static final String DEFAULT_SKILL_ICON = "transparent.png";

    private static final String DEFAULT_CATEGORY_ICON_PATH = "textures/category/";
    private static final String DEFAULT_CATEGORY_ICON = "transparent.png";

    public static ResourceLocation getCategoryIconWithDefaultPath(String fileName) {
        return new ResourceLocation(Skilled.MODID, DEFAULT_CATEGORY_ICON_PATH + fileName);
    }

    public static ResourceLocation getDefaultCategoryIcon() {
        return getCategoryIconWithDefaultPath(DEFAULT_CATEGORY_ICON_PATH);
    }

    public static ResourceLocation getSkillIconWithDefaultPath(String fileName) {
        return new ResourceLocation(Skilled.MODID, DEFAULT_SKILL_ICON_PATH + fileName);
    }

    public static ResourceLocation getDefaultSkillIcon() {
        return getSkillIconWithDefaultPath(DEFAULT_SKILL_ICON);
    }

    public static void failedSubscription(String from, String to) {
        Skilled.LOGGER.error("Failed to subscribe: " + "' '");
    }
}
