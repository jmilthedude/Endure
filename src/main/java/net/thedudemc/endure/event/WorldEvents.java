package net.thedudemc.endure.event;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.init.EndureAttributes;
import net.thedudemc.endure.init.EndureData;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import net.thedudemc.endure.util.Logger;
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
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Endure.getInstance(), () -> {
            start = System.nanoTime();
            SurvivorsData.get().tick();
            end = System.nanoTime();
            addTimeAndCalculateAvg();
        }, 0L, 1L);
    }

    private static void addTimeAndCalculateAvg() {
        ticks.add(end - start);
        start = 0;
        end = 0;
        if (ticks.size() >= 100) {
            long total = ticks.stream().mapToLong(tick -> tick).sum();
            total /= ticks.size();
            Logger.info("Average time per tick(ms):" + ((double) total / 1000000d));
            ticks = new ArrayList<>();
        }
    }

    @EventHandler
    public void onMerge(ItemMergeEvent event) {
        EndureItem item = EndureItems.getItemFromStack(event.getEntity().getItemStack());
        EndureItem target = EndureItems.getItemFromStack(event.getTarget().getItemStack());
        if (item == null || target == null) return;
        if (item != target) return;

        AttributeModifier modifier = item.getAttributeModifier(EndureAttributes.MAX_STACK_SIZE);
        if (modifier == null) return;

        int max = (int) modifier.getAmount();
        int targetCurrent = event.getTarget().getItemStack().getAmount();
        int itemCurrent = event.getEntity().getItemStack().getAmount();

        if (itemCurrent + targetCurrent > max) event.setCancelled(true);
    }
}
