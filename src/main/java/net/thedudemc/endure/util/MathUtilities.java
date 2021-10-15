package net.thedudemc.endure.util;

import org.bukkit.entity.Player;

import java.util.Random;

public class MathUtilities {

    private static final Random random = new Random();

    public static int getRandomInt(int min, int max) {
        return random.nextInt((max + 1) - min) + min;
    }

    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    public static double getSpeed(Player player) {
        double walkSpeed = 4.31d;
        double sneakMultiplier = 0.33d;
        double sprintMultiplier = 1.30d;
        if (player.isSneaking()) return walkSpeed * sneakMultiplier;
        if (player.isSprinting()) return walkSpeed * sprintMultiplier;
        return walkSpeed;
    }
}
