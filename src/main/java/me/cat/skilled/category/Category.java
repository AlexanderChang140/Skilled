package me.cat.skilled.category;

import me.cat.skilled.Skilled;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class Category {
    private static final String DEFAULT_ICON_PATH = "textures/category/";

    private final String id;
    private final ResourceLocation icon;
    private final String title;
    private final String description;

    public Category(String id, ResourceLocation icon, String title, String description) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    public Category(String id, String iconFileName, String title, String description) {
        this.id = id;
        this.icon = new ResourceLocation(Skilled.MODID, DEFAULT_ICON_PATH + iconFileName);
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void onUnlocked(ServerPlayer serverPlayer) {

    }
}
