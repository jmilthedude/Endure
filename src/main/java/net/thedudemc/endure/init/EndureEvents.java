package net.thedudemc.endure.init;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.event.*;
import org.bukkit.event.Listener;

public class EndureEvents {
    public static void register() {
        registerEvent(new PlayerEvents());
        registerEvent(new WorldEvents());
        registerEvent(new AttackEvent());
        registerEvent(new LootTableEvent());
        registerEvent(new ItemEvents());
        registerEvent(new EntityEvents());
    }

    private static void registerEvent(Listener event) {
        Endure.getInstance().getServer().getPluginManager().registerEvents(event, Endure.getInstance());
    }
}
