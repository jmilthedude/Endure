package net.thedudemc.endure.config;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;

import java.util.HashMap;

public class ExperienceConfig extends Config {

    @Expose private HashMap<Integer, Integer> levelExp;

    @Override
    public String getName() {
        return "Experience";
    }

    @Override
    protected void reset() {
        levelExp = new HashMap<>();
        int xpNeeded = 0;
        for (int i = 1; i < 58; i++) {
            if (i == 1) xpNeeded = 100;
            else if (i == 2) xpNeeded += 2550;
            else if (i == 3) xpNeeded += 1250;
            else if (i <= 5) xpNeeded += 850;
            else if (i <= 8) xpNeeded += 750;
            else if (i == 9) xpNeeded += 300;
            else if (i <= 18) xpNeeded += 450;
            else if (i <= 56) xpNeeded += 150;
            else xpNeeded += 100;

            levelExp.put(i, xpNeeded);
        }
    }

    public int getExperienceNeeded(int level) {
        if (level > 56) {
            return getOverLevelExp(level);
        }
        return levelExp.get(level) == null ? 0 : levelExp.get(level);
    }

    private int getOverLevelExp(int level) {
        int sum = levelExp.values().stream().mapToInt(i -> i).sum();
        for (int overLevel = level - 56; overLevel > 0; overLevel--) {
            sum += 100;
        }
        return sum;
    }
}
