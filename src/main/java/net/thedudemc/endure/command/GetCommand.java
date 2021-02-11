package net.thedudemc.endure.command;

import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCommand extends Command {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String id = args[0];
        EndureItem item = EndureItems.getItemById(id);
        if (item != null) {
            p.getInventory().addItem(item.getItemStack());
        }
    }
}
