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
        map.put("tickWhileStanding", Option.of(true).withComment("Should players become thirsty while standing still."));
        map.put("tickInterval", Option.of(600).withComment("How often should thirst be calculated in ticks."));
        map.put("percentTickInterval", Option.of(3d).withComment("How much thirst should be added when the interval is reached."));
        map.put("blockDistanceInterval", Option.of(50d).withComment("How many blocks to travel before thirst is added."));
        map.put("percentBlockDistanceInterval", Option.of(0.5d).withComment("How much thirst to add when block distance traveled is met."));
        map.put("percentThirstPerBottle", Option.of(25d).withComment("How much thirst to decrease when drinking water."));
        map.put("thirstDamageInterval", Option.of(80).withComment("How many ticks between damaging the player when thirst is at 0."));
        map.put("thirstDamageAmount", Option.of(1.0d).withComment("How much damage to apply when the thirst interval is reached."));

        HashMap<String, Float> biomeModifiers = new HashMap<>();
        biomeModifiers.put("desert", 2.0f);
        biomeModifiers.put("savanna", 1.5f);
        map.put("biomeModifiers", Option.of(biomeModifiers).withComment("Multiplier for how much thirst is added in specified biomes."));
        return map;
    }
}
