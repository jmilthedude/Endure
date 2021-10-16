package net.thedudemc.endure.command;

import net.thedudemc.endure.init.PluginItems;
import net.thedudemc.endure.item.EndureItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GetCommand extends Command {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1) {
            Player p = (Player) sender;
            String id = args[0];
            EndureItem item = PluginItems.getItemById(id);
            if (item != null) {
                p.getInventory().addItem(item.getItemStack());
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, String[] args) {
        if(args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], new ArrayList<>() {
                        {
                            this.addAll(PluginItems.REGISTRY.keySet());
                        }
                    },
                    new ArrayList<>(PluginItems.REGISTRY.size()));
        }
        return super.onTabComplete(sender, command, alias, args);
    }
}
