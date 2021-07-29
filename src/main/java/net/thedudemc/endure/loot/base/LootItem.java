package net.thedudemc.endure.loot.base;

import com.google.gson.annotations.Expose;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootItem {

    @Expose private final String id;
    @Expose private final int count;

    public LootItem(String id, int count) {
        this.id = id;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public ItemStack get() {
        EndureItem item = EndureItems.getItemById(this.id);
        if (item != null) return item.getItemStack(this.count);

        Material vanillaMaterial = Material.matchMaterial(this.id);
        if (vanillaMaterial != null) return new ItemStack(vanillaMaterial, this.count);

        return new ItemStack(Material.AIR);
    }
}
