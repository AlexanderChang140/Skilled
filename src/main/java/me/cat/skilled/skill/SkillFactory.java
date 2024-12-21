package me.cat.skilled.skill;

import me.cat.skilled.skill.generic.*;
import me.cat.skilled.skill.ranger.active.EnderShotSkill;
import me.cat.skilled.skill.ranger.active.MarkSkill;
import me.cat.skilled.skill.ranger.active.PowerShotSkill;
import me.cat.skilled.skill.ranger.passive.EvasionSkill;
import me.cat.skilled.skill.ranger.passive.FleetfootedSkill;
import me.cat.skilled.skill.ranger.passive.ImmobilizingShotSkill;
import me.cat.skilled.skill.ranger.passive.StealthSkill;
import me.cat.skilled.skill.warrior.active.DashSkill;
import me.cat.skilled.skill.warrior.passive.FrenzySkill;
import me.cat.skilled.skill.warrior.passive.LastStandSkill;
import me.cat.skilled.skill.warrior.passive.LifestealSkill;
import me.cat.skilled.skill.warrior.passive.ParrySkill;
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
            case SkillIds.IMMOBILIZING_SHOT -> new ImmobilizingShotSkill();
            case SkillIds.MARK -> new MarkSkill();

            // Warrior
            case SkillIds.DASH -> new DashSkill();
            case SkillIds.FRENZY -> new FrenzySkill();
            case SkillIds.PARRY -> new ParrySkill();
            case SkillIds.LIFESTEAL -> new LifestealSkill();
            case SkillIds.LAST_STAND -> new LastStandSkill();

            default -> throw new RuntimeException("Attempted to retrieve undefined skill from factory");
        };
    }
}
