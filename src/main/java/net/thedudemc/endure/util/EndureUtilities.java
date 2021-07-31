package net.thedudemc.endure.util;

import net.thedudemc.endure.init.EndureAttributes;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import org.bukkit.Material;
import org.bukkit.entity.Item;
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

    public static void dropItem(Player player, ItemStack stack) {
        Item item = player.getWorld().dropItem(player.getLocation().add(0, .75d, 0), stack);
        item.setVelocity(player.getEyeLocation().getDirection().multiply(.5d));
    }

    public static void ensureStackSizes(Player player) {
        for (ItemStack stack : player.getInventory()) {
            if (stack == null || stack.getType() == Material.AIR) continue;
            EndureItem item = EndureItems.getItemFromStack(stack);
            if (item == null) continue;
            int currentAmount = stack.getAmount();
            AttributeModifier modifier = item.getAttributeModifier(EndureAttributes.MAX_STACK_SIZE);
            if (modifier == null) continue;

            int maxStackSize = (int) item.getAttributeModifier(EndureAttributes.MAX_STACK_SIZE).getAmount();
            if (currentAmount > maxStackSize) {
                int excess = currentAmount - maxStackSize;
                stack.setAmount(maxStackSize);
                ItemStack extra = stack.clone();
                extra.setAmount(excess);
                if (EndureUtilities.addItem(player, extra, false)) {
                    player.updateInventory();
                } else {
                    player.getWorld().dropItem(player.getLocation(), extra);
                }
            }
        }
    }
}
