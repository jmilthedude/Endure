package net.thedudemc.endure.config;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.OptionMap;

public class TestConfig extends Config {

    @Override
    public String getName() {
        return "testConfig";
    }

    @Override
    public OptionMap getDefaults() {
        return OptionMap.create();
    }
}
