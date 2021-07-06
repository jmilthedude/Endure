package net.thedudemc.endure.config;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;

public class GeneralConfig extends Config {

    @Override
    public String getName() {
        return "General";
    }

    @Override
    public OptionMap getDefaults() {
        OptionMap map = OptionMap.create();
        map.put("hudUpdateInterval", Option.of(1).withComment("How often should the player hud update in ticks. This can be changed based on server performance if needed."));
        return map;
    }

}
