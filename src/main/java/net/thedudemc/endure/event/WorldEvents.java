package net.thedudemc.endure.event;

import net.thedudemc.endure.init.PluginData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldEvents implements Listener {

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        PluginData.REGISTRY.values().forEach(net.thedudemc.endure.world.data.Data::save);
    }

    @EventHandler
    public void onMonsterSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            event.setCancelled(true); // we will control spawning.
        }
    }
}
