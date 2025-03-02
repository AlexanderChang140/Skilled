package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.skill.Skill;

// LocalPlayerMixin
public class FleetfootedSkill extends Skill {
    public static final int MAX_LEVEL = 5;
    public final static float START_CROUCH_MOVEMENT_SPEED = 0.3F;
    private final static float PER_LEVEL_ADDITIVE = 0.1F;

    public FleetfootedSkill() {
        super(1);
    }

    public static float getCrouchMovementSpeed(int level) {
        return START_CROUCH_MOVEMENT_SPEED + PER_LEVEL_ADDITIVE * level;
    }
}

