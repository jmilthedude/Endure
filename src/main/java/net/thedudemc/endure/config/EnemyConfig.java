package net.thedudemc.endure.config;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;

import java.util.HashMap;

public class EnemyConfig extends Config {

    @Expose private HashMap<Integer, Integer> maxEntitiesPerLevel;
    @Expose private int spawnRate;
    @Expose private double spawnRadius;
    @Expose private double spawnChance;


    @Override
    public String getName() {
        return "Enemies";
    }

    @Override
    protected void reset() {
        maxEntitiesPerLevel = new HashMap<>();
        int count = 2;
        for (int level = 1; level <= 50; level++) {
            if (level % 5 == 0) {
                count += 2;
            }
            maxEntitiesPerLevel.put(level, count);
        }
        spawnRate = 1;
        spawnRadius = 32d;
        spawnChance = 0.25d;
    }

    public int getMaximumEntities(int level) {
        return this.maxEntitiesPerLevel.get(level);
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public double getSpawnRadius() {
        return spawnRadius;
    }

    public double getSpawnChance() {
        return spawnChance;
    }
}
