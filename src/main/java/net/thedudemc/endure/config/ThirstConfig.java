package net.thedudemc.endure.config;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;

import java.util.HashMap;

public class ThirstConfig extends Config {

    @Expose private HashMap<String, Float> BIOME_MODIFIERS = new HashMap<>();

    @Override
    public String getName() {
        return "Thirst";
    }

    @Override
    public OptionMap getDefaults() {
        OptionMap map = OptionMap.create();
        map.put("tickWhileStanding", Option.of(false));
        map.put("tickInterval", Option.of(1));
        map.put("percentTickInterval", Option.of(0.5f));
        map.put("blockDistanceInterval", Option.of(0.5f));
        map.put("percentBlockDistanceInterval", Option.of(0.5f));
        map.put("percentThirstPerBottle", Option.of(0.5f));
        return map;
    }

    public float getBiomeModifier(String biomeKey) {
        return this.BIOME_MODIFIERS.getOrDefault(biomeKey, 1f);
    }
}
