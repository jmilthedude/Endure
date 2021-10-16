package net.thedudemc.endure.init;

import net.thedudemc.endure.item.attributes.Attribute;
import net.thedudemc.endure.item.attributes.RangedAttribute;

import java.util.HashMap;

public class PluginAttributes {

    public static HashMap<String, Attribute> ATTRIBUTES = new HashMap<>();

    public static final Attribute BASE_DAMAGE = register("base_damage", (new RangedAttribute("base_damage", "Base Damage", 2.0D, 0.0D, 2048.0D)));
    public static final Attribute BONUS_DAMAGE = register("bonus_damage", (new RangedAttribute("bonus_damage", "Bonus Damage", 0.0D, 0.0D, 2048.0D)));
    public static final Attribute MAX_STACK_SIZE = register("max_stack_size", (new RangedAttribute("max_stack_size", "Max Stack Count", 1D, 1D, 64D)));

    private static Attribute register(String name, Attribute attribute) {
        ATTRIBUTES.put(name, attribute);
        return attribute;
    }

}
