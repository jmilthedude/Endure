package net.thedudemc.endure.init;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.ConfigRegistry;
import net.thedudemc.endure.Endure;
import net.thedudemc.endure.config.EnemyConfig;
import net.thedudemc.endure.config.ExperienceConfig;
import net.thedudemc.endure.config.GeneralConfig;
import net.thedudemc.endure.config.ThirstConfig;

public class EndureConfigs {

    public static ConfigRegistry REGISTRY = new ConfigRegistry(Endure.getInstance().getDataFolder() + "/config/");

    public static void register() {
        REGISTRY.register(new GeneralConfig());
        REGISTRY.register(new ThirstConfig());
        REGISTRY.register(new ExperienceConfig());
        REGISTRY.register(new EnemyConfig());
    }

    public static Config get(String name) {
        return REGISTRY.getConfig(name);
    }

}
