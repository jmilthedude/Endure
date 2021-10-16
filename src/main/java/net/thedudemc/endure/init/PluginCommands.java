package net.thedudemc.endure.init;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.command.Command;
import net.thedudemc.endure.command.DudeCommand;
import net.thedudemc.endure.command.GetCommand;
import net.thedudemc.endure.util.Logger;

public class PluginCommands {

    public static DudeCommand DUDE;
    public static GetCommand GET;

    public static void register() {
        DUDE = (DudeCommand) registerCommand(new DudeCommand()).opOnly();
        GET = (GetCommand) registerCommand(new GetCommand()).opOnly().playerOnly();

        Logger.info("Commands registered.");
    }

    private static Command registerCommand(Command command) {
        Endure.getInstance().getCommand(command.getName()).setExecutor(command);
        Endure.getInstance().getCommand(command.getName()).setTabCompleter(command);
        return command;
    }

}
