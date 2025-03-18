package me.cat.skilled.category.node;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public record NodeView(String title, Function<Integer, String> desc, ResourceLocation icon, int size, int x, int y) { }
