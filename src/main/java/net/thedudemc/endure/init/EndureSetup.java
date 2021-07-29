package net.thedudemc.endure.init;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.event.WorldEvents;
import net.thedudemc.endure.util.Logger;

public class EndureSetup {

    public static void initialize(Endure plugin) {
        Logger.info("PluginSetup#initialize()");

        EndureData.register();
        EndureItems.register();
        EndureConfigs.register();
        EndureLoot.register();
        EndureCommands.register();
        EndureEvents.register(plugin);


        WorldEvents.runGlobalTick();

    }
}
