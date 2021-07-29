package net.thedudemc.endure.init;

import net.thedudemc.endure.event.*;
import org.bukkit.plugin.java.JavaPlugin;

public class EndureEvents {
    public static void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new WorldEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AttackEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new LootTableEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ItemEvents(), plugin);
    }
}
