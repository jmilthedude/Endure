package net.thedudemc.endure.init;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.command.DudeCommand;
import net.thedudemc.endure.command.GetCommand;
import net.thedudemc.endure.command.Command;

public class EndureCommands {

    public static DudeCommand DUDE;
    public static GetCommand GET;

    public static void register() {
        DUDE = (DudeCommand) registerCommand(new DudeCommand()).opOnly();
        GET = (GetCommand) registerCommand(new GetCommand()).opOnly().playerOnly();

    }

    private static Command registerCommand(Command command) {
        Endure.getInstance().getCommand(command.getName()).setExecutor(command);
        Endure.getInstance().getCommand(command.getName()).setTabCompleter(command);
        return command;
    }

}
