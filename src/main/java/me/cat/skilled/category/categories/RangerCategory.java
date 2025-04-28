package me.cat.skilled.category.categories;

import me.cat.skilled.category.Category;
import me.cat.skilled.category.node.Connection;
import me.cat.skilled.category.node.SkillNode;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.util.Utilities;

public class RangerCategory extends Category {
    public RangerCategory() {
        super(
                "ranger",
                "Ranger",
                "Ranger",
                Utilities.getDefaultCategoryIcon()
        );
        addNode(POWER_SHOT);

        addNode(MARK);
        addNode(HOMING_SHOT);
        addNode(SHRAPNEL_BARRAGE);

        addNode(ENDER_SHOT);
        addNode(REPOSITION);
        addNode(BLINK_SLASH);

        addNode(IMMOBILIZING_SHOT);
        addNode(PIERCING_MOMENTUM);

        addNode(VOLLEY);

        addDirectionalConnection(Connection.ConnectionType.REQUIRED, MARK, POWER_SHOT);
        addDirectionalConnection(Connection.ConnectionType.REQUIRED, HOMING_SHOT, MARK);
        addDirectionalConnection(Connection.ConnectionType.REQUIRED, SHRAPNEL_BARRAGE, MARK);

        addDirectionalConnection(Connection.ConnectionType.REQUIRED, ENDER_SHOT, POWER_SHOT);
        addDirectionalConnection(Connection.ConnectionType.REQUIRED, REPOSITION, ENDER_SHOT);
        addDirectionalConnection(Connection.ConnectionType.REQUIRED, BLINK_SLASH, ENDER_SHOT);

        addDirectionalConnection(Connection.ConnectionType.REQUIRED, IMMOBILIZING_SHOT, HOMING_SHOT);
        addDirectionalConnection(Connection.ConnectionType.REQUIRED, IMMOBILIZING_SHOT, SHRAPNEL_BARRAGE);
        addDirectionalConnection(Connection.ConnectionType.REQUIRED, PIERCING_MOMENTUM, REPOSITION);
        addDirectionalConnection(Connection.ConnectionType.REQUIRED, PIERCING_MOMENTUM, BLINK_SLASH);

        addDirectionalConnection(Connection.ConnectionType.MUTUAL, VOLLEY, IMMOBILIZING_SHOT);
        addDirectionalConnection(Connection.ConnectionType.MUTUAL, VOLLEY, PIERCING_MOMENTUM);
    }

    private static final SkillNode POWER_SHOT = new SkillNode(
            "power_shot",
            true,
            0,
            1,
            SkillRegistry.POWER_SHOT,
            SkillNode.skillToNode(SkillRegistry.POWER_SHOT, 16, DEFAULT_GRID.getPos(0), DEFAULT_GRID.getPos(5))
    );

    private static final SkillNode MARK = new SkillNode(
            "mark",
            false,
            0,
            1,
            SkillRegistry.MARK,
            SkillNode.skillToNode(SkillRegistry.MARK, 16, DEFAULT_GRID.getPos(-6), DEFAULT_GRID.getPos(2))
    );

    private static final SkillNode HOMING_SHOT = new SkillNode(
            "homing_shot",
            false,
            0,
            1,
            SkillRegistry.HOMING_SHOT,
            SkillNode.skillToNode(SkillRegistry.HOMING_SHOT, 16, DEFAULT_GRID.getPos(-9), DEFAULT_GRID.getPos(-1))
    );

    private static final SkillNode SHRAPNEL_BARRAGE = new SkillNode(
            "shrapnel_barrage",
            false,
            0,
            1,
            SkillRegistry.SHRAPNEL_BARRAGE,
            SkillNode.skillToNode(SkillRegistry.SHRAPNEL_BARRAGE, 16, DEFAULT_GRID.getPos(-3), DEFAULT_GRID.getPos(-1))
    );

    private static final SkillNode ENDER_SHOT = new SkillNode(
            "ender_shot",
            false,
            0,
            1,
            SkillRegistry.ENDER_SHOT,
            SkillNode.skillToNode(SkillRegistry.ENDER_SHOT, 16, DEFAULT_GRID.getPos(6), DEFAULT_GRID.getPos(2))
    );

    private static final SkillNode REPOSITION = new SkillNode(
            "reposition",
            false,
            0,
            1,
            SkillRegistry.REPOSITION,
            SkillNode.skillToNode(SkillRegistry.REPOSITION, 16, DEFAULT_GRID.getPos(3), DEFAULT_GRID.getPos(-1))
    );

    private static final SkillNode BLINK_SLASH = new SkillNode(
            "blink_slash",
            false,
            0,
            1,
            SkillRegistry.BLINK_SLASH,
            SkillNode.skillToNode(SkillRegistry.BLINK_SLASH, 16, DEFAULT_GRID.getPos(9), DEFAULT_GRID.getPos(-1))
    );

    private static final SkillNode IMMOBILIZING_SHOT = new SkillNode(
            "immobilizing_shot",
            false,
            0,
            1,
            SkillRegistry.IMMOBILIZING_SHOT,
            SkillNode.skillToNode(SkillRegistry.IMMOBILIZING_SHOT, 16, DEFAULT_GRID.getPos(-6), DEFAULT_GRID.getPos(-4))
    );

    private static final SkillNode PIERCING_MOMENTUM = new SkillNode(
            "piercing_momentum",
            false,
            0,
            1,
            SkillRegistry.PIERCING_MOMENTUM,
            SkillNode.skillToNode(SkillRegistry.PIERCING_MOMENTUM, 16, DEFAULT_GRID.getPos(6), DEFAULT_GRID.getPos(-4))
    );

    private static final SkillNode VOLLEY = new SkillNode(
            "volley",
            false,
            0,
            1,
            SkillRegistry.VOLLEY,
            SkillNode.skillToNode(SkillRegistry.VOLLEY, 16, DEFAULT_GRID.getPos(0), DEFAULT_GRID.getPos(-7))
    );
}
