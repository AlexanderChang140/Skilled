package me.cat.skilled.registry;

import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.data.ranger.active.EnderShotData;
import me.cat.skilled.skill.data.ranger.active.MarkData;
import me.cat.skilled.skill.data.ranger.passive.*;
import me.cat.skilled.skill.data.warrior.active.DashData;
import me.cat.skilled.skill.data.warrior.active.SlashData;
import me.cat.skilled.skill.data.warrior.passive.FrenzyData;
import me.cat.skilled.skill.data.warrior.passive.LastStandData;
import me.cat.skilled.skill.data.warrior.passive.LifestealData;
import me.cat.skilled.skill.data.warrior.passive.ParryData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SkillRegistry {
    private static final Map<String, SkillData> REGISTRY = new HashMap<>();

    public static final SkillData BARRIER = register(new PowerShotData());

    public static final SkillData ENDER_SHOT = register(new EnderShotData());

    public static final SkillData MARK = register(new MarkData());

    public static final SkillData EVASION = register(new EvasionData());

    public static final SkillData FLEETFOOTED = register(new FleetfootedData());

    public static final SkillData HOMING_SHOT = register(new HomingShotData());

    public static final SkillData IMMOBILIZING_SHOT = register(new ImmobilizingShotData());

    public static final SkillData POWER_SHOT = register(new PowerShotData());

    public static final SkillData STEALTH = register(new StealthData());

    public static final SkillData PIERCING_MOMENTUM = register(new PiercingMomentumData());

    public static final SkillData SHRAPNEL_BARRAGE = register(new ShrapnelBarrageData());

    // Warrior
    public static final SkillData DASH = register(new DashData());

    public static final SkillData FRENZY = register(new FrenzyData());

    public static final SkillData LAST_STAND = register(new LastStandData());

    public static final SkillData LIFESTEAL = register(new LifestealData());

    public static final SkillData PARRY = register(new ParryData());

    public static final SkillData SLASH = register(new SlashData());

    public static SkillData getSkillData(String skillId) {
        return REGISTRY.get(skillId);
    }

    static {
        for (var skill : REGISTRY.values()) {
            skill.registerPrerequisites();
        }
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