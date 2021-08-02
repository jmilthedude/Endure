package net.thedudemc.endure.init;

import net.thedudemc.endure.util.Logger;

public class EndureSetup {

    public static void initialize() {
        Logger.info("Initializing...");

        EndureData.register();
        Logger.info("Data registered.");
        EndureItems.register();
        Logger.info("Items registered.");
        EndureConfigs.register();
        Logger.info("Configs registered.");
        EndureLoot.register();
        Logger.info("Loot registered.");
        EndureCommands.register();
        Logger.info("Commands registered.");
        EndureEvents.register();
        Logger.info("Events registered.");
        EndureTasks.register();
        Logger.info("Tasks registered.");

    }
}
