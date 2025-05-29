package me.cat.skilled.registry;

import me.cat.skilled.skill.SkillData;
import me.cat.skilled.skill.skills.paladin.active.DivineSmiteData;
import me.cat.skilled.skill.skills.paladin.active.MendWoundsSkillData;
import me.cat.skilled.skill.skills.paladin.passive.AuraOfProtectionData;
import me.cat.skilled.skill.skills.ranger.active.EnderShotData;
import me.cat.skilled.skill.skills.ranger.active.MarkData;
import me.cat.skilled.skill.skills.ranger.active.VolleyData;
import me.cat.skilled.skill.skills.ranger.passive.*;
import me.cat.skilled.skill.skills.rogue.active.VoidwalkerData;
import me.cat.skilled.skill.skills.rogue.passive.*;
import me.cat.skilled.skill.skills.warrior.active.DashData;
import me.cat.skilled.skill.skills.warrior.active.SecondWindData;
import me.cat.skilled.skill.skills.warrior.active.SlashData;
import me.cat.skilled.skill.skills.warrior.passive.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SkillRegistry {
    private static final Map<String, SkillData> REGISTRY = new HashMap<>();
    public static final SkillData BARRIER = register(new PowerShotData());

    // Ranger
    public static final SkillData ENDER_SHOT = register(new EnderShotData());
    public static final SkillData BLINK_SLASH = register(new BlinkSlashData());
    public static final SkillData MARK = register(new MarkData());
    public static final SkillData HOMING_SHOT = register(new HomingShotData());
    public static final SkillData IMMOBILIZING_SHOT = register(new ImmobilizingShotData());
    public static final SkillData POWER_SHOT = register(new PowerShotData());
    public static final SkillData PIERCING_MOMENTUM = register(new PiercingMomentumData());
    public static final SkillData SHRAPNEL_BARRAGE = register(new ShrapnelBarrageData());
    public static final SkillData VOLLEY = register(new VolleyData());
    public static final SkillData REPOSITION = register(new RepositionData());

    // Warrior
    public static final SkillData DASH = register(new DashData());
    public static final SkillData FRENZY = register(new FrenzyData());
    public static final SkillData LAST_STAND = register(new LastStandData());
    public static final SkillData LIFESTEAL = register(new LifestealData());
    public static final SkillData PARRY = register(new ParryData());
    public static final SkillData SLASH = register(new SlashData());
    public static final SkillData SECOND_WIND = register(new SecondWindData());
    public static final SkillData TENACITY = register(new TenacityData());
    public static final SkillData BLOODLUST = register(new BloodlustData());

    // Paladin
    public static final SkillData DIVINE_SMITE = register(new DivineSmiteData());
    public static final SkillData MEND_WOUNDS = register(new MendWoundsSkillData());
    public static final SkillData AURA_OF_PROTECTION = register(new AuraOfProtectionData());

    // Rogue
    public static final SkillData VOIDWALKER = register(new VoidwalkerData());
    public static final SkillData EVASION = register(new EvasionData());
    public static final SkillData FLEETFOOTED = register(new FleetfootedData());
    public static final SkillData PICKPOCKET = register(new PickpocketData());
    public static final SkillData SLOW_FALL = register(new AcrobatData());
    public static final SkillData STEALTH = register(new StealthData());

    public static SkillData getSkillData(String skillId) {
        return REGISTRY.get(skillId);
    }

    public static SkillData register(SkillData skillData) {
        REGISTRY.put(skillData.getSkillId(), skillData);
        return skillData;
    }

    public static Set<String> getSkillIds() {
        return REGISTRY.keySet();
    }

    public static Set<Map.Entry<String, SkillData>> getSkills() {
        return Collections.unmodifiableSet(REGISTRY.entrySet());
    }
}