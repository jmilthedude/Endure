package net.thedudemc.endure.util;

import net.thedudemc.endure.init.EndureAttributes;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EndureUtilities {

    public static boolean addItem(Player player, ItemStack stack, boolean simulate) {
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < 36; i++) {
            ItemStack stackInSlot = inventory.getItem(i);
            if (stackInSlot != null && stack.getType() == stackInSlot.getType()) {
                EndureItem item = EndureItems.getItemFromStack(stackInSlot);
                if (item != null) {
                    AttributeModifier modifier = item.getAttributeModifier(EndureAttributes.MAX_STACK_SIZE);
                    if (modifier != null) {
                        int max = (int) modifier.getAmount();
                        if (stackInSlot.getAmount() < max) {
                            if (!simulate) stackInSlot.setAmount(stackInSlot.getAmount() + stack.getAmount());
                            return true;
                        }
                    }
                }
            }
            if (stackInSlot == null || stackInSlot.getType() == Material.AIR) {
                if (!simulate) inventory.setItem(i, stack);
                return true;
            }
        }
        return false;
    }
}
