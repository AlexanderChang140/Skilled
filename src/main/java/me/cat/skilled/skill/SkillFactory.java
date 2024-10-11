package me.cat.skilled.skill;

import me.cat.skilled.skill.skills.*;
import me.cat.skilled.util.SkillIds;

public class SkillFactory {
    public static Skill getSkill(String skillId) {
        return switch (skillId) {
            // Ranger
            case SkillIds.ENDER_SHOT -> new EnderShotSkill();
            case SkillIds.POWER_SHOT -> new PowerShotSkill();
            case SkillIds.FLEETFOOTED -> new FleetfootedSkill();
            case SkillIds.BARRIER -> new BarrierSkill();

            // Warrior
            case SkillIds.DASH -> new DashSkill();
            case SkillIds.FRENZY -> new FrenzySkill();
            case SkillIds.PARRY -> new ParrySkill();
            case SkillIds.LIFESTEAL -> new LifestealSkill();
            case SkillIds.LAST_STAND -> new LastStandSkill();

            default -> null;
        };
    }
}
