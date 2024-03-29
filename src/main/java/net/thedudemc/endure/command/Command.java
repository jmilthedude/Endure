package net.thedudemc.endure.command;

import net.thedudemc.endure.command.exception.CommandException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Command implements CommandExecutor, TabCompleter {

    private boolean opOnly = false;
    private boolean playerCommand = false;

    public abstract String getName();

    public Command() {
    }

    public Command opOnly() {
        this.opOnly = true;
        return this;
    }

    public Command playerOnly() {
        this.playerCommand = true;
        return this;
    }

    public boolean canExecute(CommandSender sender) throws CommandException {
        if (this.playerCommand && !(sender instanceof Player))
            throw new CommandException("This command can only be run by a player.");
        if (this.opOnly && !sender.isOp())
            throw new CommandException("You do not have permission to run this command.");
        return true;
    }

    public abstract void execute(CommandSender sender, String[] args) throws CommandException;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, String[] args) {
        try {
            if (this.canExecute(sender)) this.execute(sender, args);
        } catch (CommandException ex) {
            sender.sendMessage(ChatColor.RED + ex.getMessage());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, String[] args) {
        return null;
    }

}
