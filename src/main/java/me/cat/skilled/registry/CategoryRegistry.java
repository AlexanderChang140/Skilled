package me.cat.skilled.registry;

import me.cat.skilled.skill.category.Category;
import me.cat.skilled.skill.category.RangerCategory;
import me.cat.skilled.skill.category.WarriorCategory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CategoryRegistry {
    private final static Map<String, Category> REGISTRY = new HashMap<>();

    public static final Category RANGER = register(new RangerCategory());

    public static final Category WARRIOR = register(new WarriorCategory());

    public static Category register(Category category) {
        REGISTRY.put(category.getId(), category);
        return category;
    }

    public static Category getCategory(String categoryId) {
        return REGISTRY.get(categoryId);
    }

    public static Set<String> getCategoryIds() {
        return REGISTRY.keySet();
    }

    public static Set<Map.Entry<String, Category>> getCategories() {
        return Collections.unmodifiableSet(REGISTRY.entrySet());
    }
}