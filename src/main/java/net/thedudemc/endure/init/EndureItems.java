package net.thedudemc.endure.init;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.SwordItem;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import net.thedudemc.endure.item.attributes.AttributeModifier.Operation;
import net.thedudemc.endure.util.Rarity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class EndureItems {

    public static HashMap<String, EndureItem> REGISTRY = new HashMap<>();

    public static EndureItem DINKY_SWORD;
    public static EndureItem EMPTY_BOTTLE;

    public static void register() {
        DINKY_SWORD = registerItem(new SwordItem("dinky_sword", "Dinky Sword", Material.WOODEN_SWORD, Rarity.COMMON)
                .withAttribute(EndureAttributes.BONUS_DAMAGE, new AttributeModifier("Bonus Damage", 12.0D, Operation.ADDITION))
                .withAttribute(EndureAttributes.BASE_DAMAGE, new AttributeModifier("Base Damage", 2.0D, Operation.ADDITION))
        );
        EMPTY_BOTTLE = registerItem(new EndureItem("empty_bottle", "Empty Bottle", Material.GLASS_BOTTLE))
                .withAttribute(EndureAttributes.MAX_STACK_SIZE, new AttributeModifier(EndureAttributes.MAX_STACK_SIZE.getDisplayName(), 2.0D, Operation.ADDITION));
    }

    private static EndureItem registerItem(EndureItem item) {
        REGISTRY.put(item.getId(), item);
        return item;
    }

    public static EndureItem getItemById(String id) {
        return REGISTRY.get(id);
    }

    public static EndureItem getItemFromStack(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return null;
        String id = meta.getPersistentDataContainer().get(Endure.getKey("ItemID"), PersistentDataType.STRING);
        return getItemById(id);
    }
}
