package net.thedudemc.endure.event;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.init.EndureData;
import net.thedudemc.endure.world.data.Data;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldEvents implements Listener {

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        EndureData.REGISTRY.values().forEach(Data::save);
    }

    public static void runGlobalTick() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Endure.getInstance(), () -> SurvivorsData.get().tick(), 0L, 1L);
    }

    @EventHandler
    public void onMonsterSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Phantom) event.setCancelled(true);
        if (event.getEntity() instanceof Slime) event.setCancelled(true);
        if (event.getEntity() instanceof Animals) event.setCancelled(true);
        if (event.getEntity() instanceof WaterMob) event.setCancelled(true);

        if (event.getEntity() instanceof Monster && event.getEntityType() != EntityType.ZOMBIE) {
            event.setCancelled(true);
            Location location = event.getLocation();
            location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        }

    }
}
