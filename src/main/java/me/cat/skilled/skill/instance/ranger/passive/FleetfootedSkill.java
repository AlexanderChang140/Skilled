package me.cat.skilled.skill.instance.ranger.passive;

import me.cat.skilled.skill.instance.Skill;

// LocalPlayerMixin
public class FleetfootedSkill extends Skill {
    public static final int MAX_LEVEL = 5;
    public final static float START_CROUCH_MOVEMENT_SPEED = 0.3F;
    private final static float PER_LEVEL_ADDITIVE = 0.1F;

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static float getCrouchMovementSpeed(int level) {
        return START_CROUCH_MOVEMENT_SPEED + PER_LEVEL_ADDITIVE * level;
    }
}

