package net.thedudemc.endure.config;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;

import java.util.HashMap;

public class EnemyConfig extends Config {

    @Expose private HashMap<Integer, Integer> maxEntitiesPerLevel;
    @Expose private int spawnRate;
    @Expose private int minSpawnRadius;
    @Expose private int maxSpawnRadius;
    @Expose private int maxHeightDifference;
    @Expose private double spawnChance;


    @Override
    public String getName() {
        return "Enemies";
    }

    @Override
    protected void reset() {
        maxEntitiesPerLevel = new HashMap<>();
        int count = 4;
        for (int level = 1; level <= 50; level++) {
            if (level % 5 == 0) {
                count += 5;
            }
            maxEntitiesPerLevel.put(level, count);
        }
        spawnRate = 1;
        minSpawnRadius = 16;
        maxSpawnRadius = 32;
        maxHeightDifference = 16;
        spawnChance = 0.25d;
    }

    public int getMaximumEntities(int level) {
        return this.maxEntitiesPerLevel.get(level);
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public int getMinSpawnRadius() {
        return minSpawnRadius;
    }

    public int getMaxSpawnRadius() {
        return maxSpawnRadius;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public int getMaxHeightDifference() {
        return maxHeightDifference;
    }
}
