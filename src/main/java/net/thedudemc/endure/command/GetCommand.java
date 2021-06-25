package net.thedudemc.endure.command;

import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
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
            EndureItem item = EndureItems.getItemById(id);
            if (item != null) {
                p.getInventory().addItem(item.getItemStack());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if(args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], new ArrayList<String>() {
                        {
                            this.addAll(EndureItems.REGISTRY.keySet());
                        }
                    },
                    new ArrayList<>(EndureItems.REGISTRY.size()));
        }
        return super.onTabComplete(sender, command, alias, args);
    }
}
