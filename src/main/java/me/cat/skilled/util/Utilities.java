package me.cat.skilled.util;

import me.cat.skilled.Skilled;
import net.minecraft.resources.ResourceLocation;

public class Utilities {
    private static final String DEFAULT_ICON_PATH = "textures/skill/";
    private static final String DEFAULT_ICON = "transparent.png";

    public static ResourceLocation getSkillIconWithDefaultPath(String fileName) {
        return new ResourceLocation(Skilled.MODID, DEFAULT_ICON_PATH + fileName);
    }

    public static ResourceLocation getDefaultSkillIcon() {
        return getSkillIconWithDefaultPath(DEFAULT_ICON);
    }

    public static void failedSubscription(String from, String to) {
        Skilled.LOGGER.error("Failed to subscribe: " + "' '");
    }
}
