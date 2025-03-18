package me.cat.skilled.skill.component;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class GuiData {
    private final String title;
    private final Function<Integer, String> desc;
    private final ResourceLocation icon;
    private final int size;
    private final int x;
    private final int y;

    public GuiData(String title, Function<Integer, String> desc, ResourceLocation icon, int size, int x, int y) {
        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc(int level) {
        return desc.apply(level);
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
