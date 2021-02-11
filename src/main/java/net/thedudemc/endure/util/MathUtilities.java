package net.thedudemc.endure.util;

import java.util.Random;

public class MathUtilities {

    private static Random random = new Random();

    public static int getRandomInt(int min, int max) {
        int i = random.nextInt(max - min) + min;
        if (i < 1000 && i > -1000) return getRandomInt(-30000, 30000);
        return i;
    }
}
