package net.thedudemc.endure.event;

import net.thedudemc.endure.init.PluginAttributes;
import net.thedudemc.endure.init.PluginItems;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.attributes.EndureModifier;
import net.thedudemc.endure.util.EndureUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemMergeEvent;

public class ItemEvents implements Listener {


    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        EndureItem item = PluginItems.getItemFromStack(event.getItem().getItemStack());
        if (item == null) return;

        EndureModifier modifier = item.getAttributeModifier(PluginAttributes.MAX_STACK_SIZE);
        if (modifier == null) return;

        if (!EndureUtilities.addItem(player, event.getItem().getItemStack(), true)) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onMerge(ItemMergeEvent event) {
        EndureItem item = PluginItems.getItemFromStack(event.getEntity().getItemStack());
        EndureItem target = PluginItems.getItemFromStack(event.getTarget().getItemStack());
        if (item == null || target == null) return;
        if (item != target) return;

        EndureModifier modifier = item.getAttributeModifier(PluginAttributes.MAX_STACK_SIZE);
        if (modifier == null) return;

        int max = (int) modifier.getAmount();
        int targetCurrent = event.getTarget().getItemStack().getAmount();
        int itemCurrent = event.getEntity().getItemStack().getAmount();

        if (itemCurrent + targetCurrent > max) event.setCancelled(true);
    }
}
