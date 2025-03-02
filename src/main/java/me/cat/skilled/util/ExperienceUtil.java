package me.cat.skilled.util;

public class ExperienceUtil {
    private static final int BASE = 100;
    private static final int D_1 = 50;
    private static final int D_2 = 100;
    private static final int BREAKPOINT_1 = 5;
    private static final int EXPERIENCE_TO_LEVEL_5 = levelToExperience(5);

    public static int levelToExperience(int level) {
        int experience = 0;
        experience += arithmeticSum(Math.min(level, BREAKPOINT_1), BASE, D_1);
        if (level > BREAKPOINT_1) {
            experience += arithmeticSum(level - BREAKPOINT_1, 0, D_2);
        }
        return experience;
    }

    public static int experienceToLevel(int experience) {
        int level = 0;
        level += inverseArithmeticSum(Math.min(experience, EXPERIENCE_TO_LEVEL_5), BASE, D_1);
        if (experience > EXPERIENCE_TO_LEVEL_5) {
            level += inverseArithmeticSum(experience - EXPERIENCE_TO_LEVEL_5, 0, D_2);
        }
        return level;
    }

    private static int arithmeticSum(int n, int a, int d) {
        return (n / 2) * (2 * a + (n - 1) * d);
    }

    private static int inverseArithmeticSum(int experience, int a, int d) {
        return solveQuadratic(d, 2 * a - d, -2 * experience);
    }

    private static int solveQuadratic(int a, int b, int c) {
        return (int) ((-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a));
    }
}