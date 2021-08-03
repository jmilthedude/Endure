package net.thedudemc.endure.item;

import net.thedudemc.endure.init.EndureAttributes;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import net.thedudemc.endure.util.Rarity;
import org.bukkit.Material;

public class WeaponItem extends EndureItem {

    public WeaponItem(String id, String displayName, Material material, Rarity rarity) {
        super(id, displayName, material, rarity);
    }

    public double getDamage() {
        AttributeModifier modifier = this.getAttributeModifier(EndureAttributes.BASE_DAMAGE);
        if (modifier != null) {
            return modifier.getAmount();
        }
        return 0;
    }

    public double getBonusDamage() {
        AttributeModifier modifier = this.getAttributeModifier(EndureAttributes.BONUS_DAMAGE);
        if (modifier != null) {
            return modifier.getAmount();
        }
        return 0;
    }

}
