package net.thedudemc.endure.init;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.ConfigRegistry;
import net.thedudemc.endure.Endure;
import net.thedudemc.endure.config.TestConfig;

public class EndureConfigs {

    public static ConfigRegistry REGISTRY = new ConfigRegistry(Endure.getInstance().getDataFolder() + "/config/");

    public static void register() {
        REGISTRY.register(new TestConfig());

        REGISTRY.getConfig("testConfig").putInt("someInt", 5);
    }

    public static Config get(String name) {
        return REGISTRY.getConfig(name);
    }

}
