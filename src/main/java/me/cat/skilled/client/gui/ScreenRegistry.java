package me.cat.skilled.client.gui;

import net.minecraft.client.gui.screens.Screen;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ScreenRegistry {
    private static final Map<String, ScreenData> REGISTRY = new HashMap<>();

    public static final ScreenData CATEGORY = register(new ScreenData("category", CategoryScreen::new));
    public static final ScreenData SKILL = register(new ScreenData("skill", SkillScreen::new));

    public static ScreenData register(ScreenData screenData) {
        REGISTRY.put(screenData.getScreenId(), screenData);
        return screenData;
    }

    public static Screen getScreen(String screenId) {
        return REGISTRY.get(screenId).getScreen();
    }

    public static class ScreenData {
        private final String screenId;
        private final Supplier<Screen> screenSupplier;

        public ScreenData(String screenId, Supplier<Screen>  screenSupplier) {
            this.screenId = screenId;
            this.screenSupplier = screenSupplier;
        }

        public String getScreenId() {
            return screenId;
        }

        public Screen getScreen() {
            return screenSupplier.get();
        }
    }
}
