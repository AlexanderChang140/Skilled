package me.cat.skilled.experience;


import me.cat.skilled.util.MathUtil;

public class ExperienceUtil {
    private static final int BASE = 100;
    private static final int D_1 = 50;
    private static final int D_2 = 100;
    private static final int BREAKPOINT_1 = 5;
    private static final int EXPERIENCE_TO_LEVEL_5 = levelToExperience(5);

    public static int levelToExperience(int level) {
        int experience = 0;
        experience += MathUtil.arithmeticSum(Math.min(level, BREAKPOINT_1), BASE, D_1);
        if (level > BREAKPOINT_1) {
            experience += MathUtil.arithmeticSum(level - BREAKPOINT_1, 0, D_2);
        }
        return experience;
    }

    public static int experienceToLevel(int experience) {
        int level = 0;
        level += MathUtil.inverseArithmeticSum(Math.min(experience, EXPERIENCE_TO_LEVEL_5), BASE, D_1);
        if (experience > EXPERIENCE_TO_LEVEL_5) {
            level += MathUtil.inverseArithmeticSum(experience - EXPERIENCE_TO_LEVEL_5, 0, D_2);
        }
        return level;
    }
}