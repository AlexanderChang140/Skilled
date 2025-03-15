package me.cat.skilled.util;

import net.minecraft.world.phys.Vec3;

public class MathUtil {
    public static int arithmeticSum(int n, int a, int d) {
        return (n / 2) * (2 * a + (n - 1) * d);
    }

    public static int inverseArithmeticSum(int sum, int a, int d) {
        return solveQuadratic(d, 2 * a - d, -2 * sum);
    }

    public static int solveQuadratic(int a, int b, int c) {
        return (int) ((-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a));
    }

    public static double calculateAngleBetween(Vec3 vec1, Vec3 vec2) {
        double dot = vec1.dot(vec2);
        double mag1 = vec1.length();
        double mag2 = vec2.length();

        if (mag1 == 0 || mag2 == 0) {
            return 0.0;
        }

        double cos = Math.max(-1.0, Math.min(1.0, dot / (mag1 * mag2)));
        double angleRadians = Math.acos(cos);

        return Math.toDegrees(angleRadians);
    }
}
