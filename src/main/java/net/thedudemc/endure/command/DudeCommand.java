package net.thedudemc.endure.command;

import net.thedudemc.endure.config.TestConfig;
import net.thedudemc.endure.init.EndureConfigs;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DudeCommand extends Command {

    @Override
    public String getName() {
        return "dude";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        TestConfig config = (TestConfig) EndureConfigs.REGISTRY.getConfig("testConfig");

        config.putInt("someInt", 666);
        EndureConfigs.REGISTRY.saveConfig("testConfig");

        sender.sendMessage(ChatColor.GREEN + "Dude!" + config.getInt("someInt"));
    }

}
