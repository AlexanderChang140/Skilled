package me.cat.skilled.skill;

import me.cat.skilled.skill.generic.*;
import me.cat.skilled.skill.ranger.*;
import me.cat.skilled.skill.warrior.*;
import me.cat.skilled.util.SkillIds;

public class SkillFactory {
    public static Skill getSkill(String skillId) {
        return switch (skillId) {
            // Generic
            case SkillIds.BARRIER -> new BarrierSkill();

            // Ranger
            case SkillIds.ENDER_SHOT -> new EnderShotSkill();
            case SkillIds.POWER_SHOT -> new PowerShotSkill();
            case SkillIds.FLEETFOOTED -> new FleetfootedSkill();
            case SkillIds.STEALTH -> new StealthSkill();
            case SkillIds.EVASION -> new EvasionSkill();

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
