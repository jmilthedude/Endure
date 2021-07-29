package net.thedudemc.endure.util;

import java.util.Random;

public class MathUtilities {

    private static final Random random = new Random();

    public static int getRandomInt(int min, int max) {
        return random.nextInt((max + 1) - min) + min;
    }

    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }
}
