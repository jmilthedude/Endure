package net.thedudemc.endure.item;

import net.thedudemc.endure.init.PluginAttributes;
import net.thedudemc.endure.item.attributes.EndureModifier;
import net.thedudemc.endure.util.Rarity;
import org.bukkit.Material;

public class WeaponItem extends EndureItem {

    public WeaponItem(String id, String displayName, Material material, Rarity rarity) {
        super(id, displayName, material, rarity);
    }

    public double getDamage() {
        EndureModifier modifier = this.getAttributeModifier(PluginAttributes.BASE_DAMAGE);
        if (modifier != null) {
            return modifier.getAmount();
        }
        return 0;
    }

    public double getBonusDamage() {
        EndureModifier modifier = this.getAttributeModifier(PluginAttributes.BONUS_DAMAGE);
        if (modifier != null) {
            return modifier.getAmount();
        }
        return 0;
    }

}
