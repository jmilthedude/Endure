package net.thedudemc.endure.event;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.init.EndureAttributes;
import net.thedudemc.endure.init.EndureData;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import net.thedudemc.endure.world.data.Data;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldEvents implements Listener {

    private static long start = 0;
    private static long end = 0;
    public static List<Long> ticks = new ArrayList<>();

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        EndureData.REGISTRY.values().forEach(Data::save);
    }

    public static void runWorldTick() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Endure.getInstance(), () -> SurvivorsData.get().tick(), 0L, 1L);
    }
}
