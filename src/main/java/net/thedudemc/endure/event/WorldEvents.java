package net.thedudemc.endure.event;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.init.EndureData;
import net.thedudemc.endure.world.data.Data;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldEvents implements Listener {

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        EndureData.REGISTRY.values().forEach(Data::save);
    }

    public static void runGlobalTick() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Endure.getInstance(), () -> SurvivorsData.get().tick(), 0L, 1L);
    }
}
