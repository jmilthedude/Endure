package net.thedudemc.endure.config;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.OptionMap;

public class GeneralConfig extends Config {

    @Override
    public String getName() {
        return "General";
    }

    @Override
    public OptionMap getDefaults() {
        return OptionMap.create();
    }

}
