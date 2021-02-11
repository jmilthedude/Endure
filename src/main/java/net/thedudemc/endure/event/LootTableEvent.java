package net.thedudemc.endure.event;

import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.util.Logger;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LootTableEvent implements Listener {

    @EventHandler
    public void onLootTable(LootGenerateEvent event) {
        //TODO: get location and player level and base loot off of that
        Logger.info(event.getLootTable().toString());
        List<ItemStack> loot = new ArrayList<>();
        loot.add(EndureItems.DINKY_SWORD.getItemStack());
        loot.add(new ItemStack(Material.GOLD_BLOCK));
        loot.add(new ItemStack(Material.DIAMOND_BLOCK));
        event.setLoot(loot);

    }
}
