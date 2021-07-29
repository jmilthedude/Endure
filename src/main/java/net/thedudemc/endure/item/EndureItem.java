package net.thedudemc.endure.item;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.item.attributes.Attribute;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import net.thedudemc.endure.util.Rarity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EndureItem {

    private final String id;
    private final String displayName;
    private final Material material;
    private final Rarity rarity;

    protected HashMap<Attribute, AttributeModifier> attributeModifiers = new HashMap<>();

    public EndureItem(String id, String displayName, Material material, Rarity rarity) {
        this.displayName = displayName;
        this.id = id;
        this.material = material;
        this.rarity = rarity;
    }

    public EndureItem(String id, String displayName, Material material) {
        this(id, displayName, material, Rarity.COMMON);
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return this.getRarity().getColor() + displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ItemStack getItemStack() {
        ItemStack stack = this.getBaseItem();
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return stack;
        meta.setDisplayName(this.getDisplayName());
        meta.setLore(this.getInfo());
        stack.setItemMeta(meta);
        return stack;
    }

    protected List<String> getInfo() {
        List<String> info = new ArrayList<>();
        if (!attributeModifiers.isEmpty()) {
            NumberFormat nf = new DecimalFormat("##.##");
            for (Attribute attribute : this.attributeModifiers.keySet()) {
                AttributeModifier modifier = attributeModifiers.get(attribute);
                info.add(attributeModifiers.get(attribute).getName() + ": " + nf.format(modifier.getAmount()));
            }
        }
        return info;
    }

    protected ItemStack getBaseItem() {
        ItemStack stack = new ItemStack(this.material);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            PersistentDataAdapterContext context = meta.getPersistentDataContainer().getAdapterContext();
            PersistentDataContainer tag = context.newPersistentDataContainer();

            meta.getPersistentDataContainer().set(Endure.getKey("id"), PersistentDataType.STRING, this.getId());
            attributeModifiers.forEach((k, v) -> tag.set(Endure.getKey(k.getAttributeName()), PersistentDataType.DOUBLE, v.getAmount()));
            meta.getPersistentDataContainer().set(Endure.getKey("Attributes"), PersistentDataType.TAG_CONTAINER, tag);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public EndureItem withAttribute(Attribute attribute, AttributeModifier modifier) {
        attributeModifiers.put(attribute, modifier);
        return this;
    }

    public AttributeModifier getAttributeModifier(Attribute attribute) {
        return attributeModifiers.get(attribute);
    }

    public HashMap<Attribute, AttributeModifier> getAttributeModifiers() {
        return attributeModifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndureItem that = (EndureItem) o;
        return getId().equals(that.getId()) && getMaterial() == that.getMaterial() && getRarity() == that.getRarity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDisplayName(), getMaterial(), getRarity());
    }
}
