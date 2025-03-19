package me.cat.skilled.category.categories;

import me.cat.skilled.category.Category;
import me.cat.skilled.category.node.Node;
import me.cat.skilled.category.node.NodeView;
import me.cat.skilled.category.node.SkillNode;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.util.Utilities;

public class RangerCategory extends Category {
    public RangerCategory() {
        super(
                "ranger",
                "ranger.png",
                "Ranger",
                "N/A"
        );

        addNode(createEnderShotNode());
    }

    private static Node createEnderShotNode() {
        return new SkillNode(
                SkillRegistry.ENDER_SHOT.getSkillId(),
                new NodeView(
                        "Ender Shot",
                        (level) -> "Teleport to the position of your next shot.",
                        Utilities.getDefaultSkillIconPath("ender_shot.png"),
                        16,
                        0,
                        0
                ),
                1,
                SkillRegistry.ENDER_SHOT.getSkillId()
        );
    }
}
