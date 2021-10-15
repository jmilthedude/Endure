package net.thedudemc.endure.event;

import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.EndureLoot;
import net.thedudemc.endure.loot.base.LootItem;
import net.thedudemc.endure.util.Logger;
import net.thedudemc.endure.util.MathUtilities;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LootTableEvent implements Listener {

    @EventHandler
    public void onLootTable(LootGenerateEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(player);
        //TODO: get location and player level and base loot off of that
        Logger.info(event.getLootTable().toString());
        List<ItemStack> items;
        if (survivor.getLevel() < 5) {
            items = EndureLoot.COMMON.getLoot(MathUtilities.getRandomInt(0, 3)).stream()
                    .map(LootItem::get)
                    .collect(Collectors.toList()
                    );
        } else if (survivor.getLevel() < 10) {
            items = EndureLoot.UNCOMMON.getLoot(MathUtilities.getRandomInt(0, 3)).stream()
                    .map(LootItem::get)
                    .collect(Collectors.toList()
                    );
        } else {
            items = new ArrayList<>();
        }
        event.setLoot(items);

    }
}
