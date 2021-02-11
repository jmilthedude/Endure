package net.thedudemc.endure.init;

import net.thedudemc.endure.config.GeneralConfig;

public class EndureConfigs {

    public static GeneralConfig GENERAL;

    public static void register() {
        GENERAL = (GeneralConfig) new GeneralConfig().readConfig();
    }
}
