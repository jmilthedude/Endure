package net.thedudemc.endure.config;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;

import java.util.HashMap;
import java.util.Map;

public class EnemyConfig extends Config {
    @Override
    public String getName() {
        return "Enemies";
    }

    @Override
    public OptionMap getDefaults() {
        OptionMap map = OptionMap.create();
        HashMap<Integer, Integer> maxEntitiesPerPlayer = new HashMap<>();
        int count = 2;
        for (int level = 1; level <= 50; level++) {
            if (level % 5 == 0) {
                count += 2;
            }
            maxEntitiesPerPlayer.put(level, count);
        }
        map.put("maxEntitiesPerPlayer", Option.of(maxEntitiesPerPlayer)
                .withComment("The maximum number of entites per player level around the player before the player will stop spawning enemies.")
        );

        map.put("radius", Option.of(64).withComment("How far away to spawn and entities from the player."));
        map.put("frequency", Option.of(1).withComment("How often (in ticks) to attempt to spawn entities around the player."));
        map.put("chanceToSpawn", Option.of(0.25d).withComment("The chance to spawn an entity when the frequency is hit."));

        return map;
    }

    public int getMaximumEntities(int level) {
        HashMap<?, ?> map = this.getOption("maxEntitiesPerPlayer").getMapValue();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            int cfgLevel = 0;
            if (entry.getKey() instanceof Integer) {
                cfgLevel = (int) entry.getKey();
            } else if (entry.getKey() instanceof String) {
                cfgLevel = Integer.parseInt((String) entry.getKey());
            }
            if (entry.getValue() instanceof Number) {
                if ((cfgLevel - 1) == level) return ((Number) entry.getValue()).intValue();
            }
        }
        return 0;
    }

    public int getSpawnFrequency() {
        return this.getOption("frequency").getIntValue();
    }

    public double getSpawnRadius() {
        return this.getOption("radius").getDoubleValue();
    }

    public double getSpawnChance() {
        return this.getOption("chanceToSpawn").getDoubleValue();
    }
}
