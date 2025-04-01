package me.cat.skilled.category.categories;

import me.cat.skilled.category.Category;
import me.cat.skilled.category.node.SkillNode;
import me.cat.skilled.registry.SkillRegistry;

public class RangerCategory extends Category {
    public RangerCategory() {
        super(
                "ranger",
                "ranger.png",
                "Ranger",
                "N/A"
        );
        addNode(ENDER_SHOT);
    }

    private static final SkillNode ENDER_SHOT = new SkillNode(
            "ender_shot",
            true,
            0,
            1,
            SkillRegistry.ENDER_SHOT,
            SkillNode.skillToNode(SkillRegistry.ENDER_SHOT, 16, 0, 0)
    );
}
