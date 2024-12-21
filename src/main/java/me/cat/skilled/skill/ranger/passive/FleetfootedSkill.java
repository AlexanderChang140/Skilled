package me.cat.skilled.skill.ranger.passive;

import me.cat.skilled.skill.Skill;

// LocalPlayerMixin
public class FleetfootedSkill extends Skill {
    public final static float START_CROUCH_MOVEMENT_SPEED = 0.3F;
    private final static float PER_LEVEL_ADDITIVE = 0.1F;

    public FleetfootedSkill() {
        super(3);
    }

    public float getCrouchMovementSpeed() {
        return START_CROUCH_MOVEMENT_SPEED + PER_LEVEL_ADDITIVE * level;
    }
}

