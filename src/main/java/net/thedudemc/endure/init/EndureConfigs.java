package net.thedudemc.endure.init;

import net.thedudemc.endure.config.GeneralConfig;
import net.thedudemc.endure.config.ThirstConfig;

public class EndureConfigs {

    public static GeneralConfig GENERAL;
    public static ThirstConfig THIRST;

    public static void register() {
        GENERAL = (GeneralConfig) new GeneralConfig().readConfig();
        THIRST = (ThirstConfig) new ThirstConfig().readConfig();
    }
}
