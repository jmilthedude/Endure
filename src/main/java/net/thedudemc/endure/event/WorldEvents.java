package net.thedudemc.endure.event;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.init.EndureData;
import net.thedudemc.endure.util.Logger;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

import java.io.IOException;
import java.util.Arrays;

public class WorldEvents implements Listener {

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        Logger.info("Saving World...");
        EndureData.REGISTRY.values().forEach(data -> {
            try {
                data.write();
            } catch (IOException e) {
                Logger.error(e.getMessage());
                Logger.error(Arrays.toString(e.getStackTrace()));
            }
        });
    }

    public static void runWorldTick() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Endure.getInstance(), () -> SurvivorsData.get().tick(), 0L, 1L);
    }
}
