package net.thedudemc.endure.init;

import net.thedudemc.endure.event.AttackEvent;
import net.thedudemc.endure.event.LootTableEvent;
import net.thedudemc.endure.event.PlayerEvents;
import net.thedudemc.endure.event.WorldEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class EndureEvents {
    public static void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new WorldEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AttackEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new LootTableEvent(), plugin);
    }
}
