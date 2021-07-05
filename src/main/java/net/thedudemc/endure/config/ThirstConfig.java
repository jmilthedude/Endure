package net.thedudemc.endure.config;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;

import java.util.HashMap;

public class ThirstConfig extends Config {

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
        map.put("biomeModifiers", Option.of(new HashMap<String, Float>() {
            {
                put("desert", 2.0f);
                put("savanna", 1.5f);
            }
        }));
        return map;
    }
}
