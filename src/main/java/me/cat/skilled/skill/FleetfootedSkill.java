package me.cat.skilled.skill;

import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.world.entity.player.Player;

public class FleetfootedSkill {
    public final static float CROUCH_MOVEMENT_SPEED = 1;

    public static boolean hasSkill(Player player) {
        return SkillUtil.hasSkill(player, SkillIds.FLEETFOOTED_SKILL);
    }
}

